package com.system.service.impl;

import com.system.dto.LoginRequestDTO;
import com.system.dto.LoginResponseDTO;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.User;
import com.system.model.UserRole;
import com.system.repository.UserRepository;
import com.system.repository.UserRoleRepository;
import com.system.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.jasypt.util.text.BasicTextEncryptor;
import com.system.audit.EntityAuditorAware;


@Service
@Data
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BasicTextEncryptor textEncryptor;

    /**
     * Checking login credentials
     *
     * @param loginRequestDTO
     * @return
     */
    @Override
    public LoginResponseDTO userLogin(LoginRequestDTO loginRequestDTO) throws UserNotFoundException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        User user = userRepository.findByEmail(loginRequestDTO.getEmail());
        if (user != null && textEncryptor.decrypt(user.getPassword()).equals(loginRequestDTO.getPassword())) {
            UserRole userRole = userRoleRepository.findByUser(user);
            loginResponseDTO.setRole(userRole.getRole().getRole());
            loginResponseDTO.setUserName((user.getName()));
            loginResponseDTO.setEmail(user.getEmail());
            EntityAuditorAware.sessionEmail = user.getEmail();
        } else {
            throw new UserNotFoundException("Invalid login credentials");
        }
        return loginResponseDTO;
    }
}