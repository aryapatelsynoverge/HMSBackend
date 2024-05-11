package com.system.service;

import com.system.dto.RoleDTO;
import com.system.dto.RoleResponseDTO;
import com.system.model.Role;

import java.util.List;

public interface RoleService {

    void saveOrUpdateRole(RoleDTO roleDTO) throws Exception;

    List<Role> getAllRoles() throws Exception;

    RoleResponseDTO getRoleById(long id) throws Exception;

    void delete(long id) throws Exception;

}
