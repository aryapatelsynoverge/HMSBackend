package com.system.service.impl;

import com.system.dto.RoleDTO;
import com.system.dto.RoleResponseDTO;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.Role;
import com.system.model.User;
import com.system.model.UserRole;
import com.system.repository.RoleRepository;
import com.system.repository.UserRoleRepository;
import com.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * Save or update role
     *
     * @param roleDTO
     * @throws Exception
     */
    @Override
    public void saveOrUpdateRole(RoleDTO roleDTO) throws Exception {
        if (roleDTO.getId() == null) {
            Role role = convertDTOtoModel(roleDTO);
            roleRepository.save(role);
        } else {
            Role existingRole = roleRepository.findById(roleDTO.getId())
                    .orElseThrow(() -> new UserNotFoundException("Role not found with id : " + roleDTO.getId()));
            existingRole.setRole(roleDTO.getRole());
            existingRole.setDescription(roleDTO.getDescription());
            roleRepository.save(existingRole);
        }
    }

    /**
     * get all roles
     *
     * @return
     */
    @Override
    public List<Role> getAllRoles() throws Exception {
        return new ArrayList<>(roleRepository.findAll());
    }

    /**
     * Get role by ID
     *
     * @param id
     * @return
     */
    @Override
    public RoleResponseDTO getRoleById(long id) throws Exception {
        Role existingRole = roleRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Role not found with id : " + id));
        List<UserRole> userRoleList = userRoleRepository.findByRole(existingRole);
        List<User> userList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            userList.add(userRole.getUser());
        }
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setId(existingRole.getId());
        roleResponseDTO.setRole(existingRole.getRole());
        roleResponseDTO.setDescription(existingRole.getDescription());
        roleResponseDTO.setUserList(userList);
        return roleResponseDTO;

    }


    /**
     * Delete role by ID
     *
     * @param id
     */
    @Override
    public void delete(long id) throws Exception {
        Role existingRole = roleRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Role not found with id : " + id));
        roleRepository.delete(existingRole);
    }

    /**
     * DTO to model
     *
     * @param roleDTO
     * @return
     */
    private Role convertDTOtoModel(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRole(roleDTO.getRole());
        role.setDescription(roleDTO.getDescription());
        return role;
    }
}
