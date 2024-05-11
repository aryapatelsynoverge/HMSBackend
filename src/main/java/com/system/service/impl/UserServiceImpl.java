package com.system.service.impl;

import com.system.dto.UserDTO;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.User;
import com.system.repository.UserRepository;
import com.system.audit.EntityAuditorAware;

import com.system.service.UserService;
import lombok.AllArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BasicTextEncryptor textEncryptor;

    /**
     * Add or update user
     *
     * @param userDTO
     * @return
     * @throws Exception
     */
    @Override
    public void editUser(UserDTO userDTO) throws Exception {

        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + userDTO.getId()));
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        EntityAuditorAware.sessionEmail = userDTO.getEmail();
        userRepository.save(existingUser);
    }

    /**
     * get user by ID
     *
     * @param loggedEmail
     * @return
     * @throws Exception
     */
    @Override
    public UserDTO getUserByEmail(String loggedEmail) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loggedEmail));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return convertModelToDTO(user.get());
    }

    /**
     * Delete user by ID
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        userRepository.delete(existingUser);
    }

    @Override
    public void setPassword(String userEmail, String currentPassword, String newPassword) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if (textEncryptor.decrypt(user.get().getPassword()).equals(currentPassword)) {
            User existingUser = user.get();
            String encryptedPassword = textEncryptor.encrypt(newPassword);
            existingUser.setPassword(encryptedPassword);
            userRepository.save(existingUser);
        } else {
            throw new Exception("Your current password is incorrect");
        }
    }

    private UserDTO convertModelToDTO(User user) {
        return new UserDTO(user);
    }
}
