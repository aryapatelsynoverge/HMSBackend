package com.system.controller;

import com.system.dto.RoleDTO;
import com.system.dto.RoleResponseDTO;
import com.system.model.Role;
import com.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;

    /**
     * Adding or updating role
     *
     * @param roleDTO
     * @return
     */
    @RequestMapping(value = "/role", method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<String> saveRole(@RequestBody RoleDTO roleDTO) throws Exception {
        roleService.saveOrUpdateRole(roleDTO);
        return new ResponseEntity<>("Role added successfully", HttpStatus.OK);
    }

    /**
     * Get all roles
     *
     * @return
     */
    @GetMapping("/role")
    private ResponseEntity<List<Role>> getAllRoles() throws Exception {
        List<Role> role = roleService.getAllRoles();
        return ResponseEntity.ok().body(role);
    }

    /**
     * Get role by id
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("/role/{roleId}")
    private ResponseEntity<RoleResponseDTO> getRole(@PathVariable("roleId") long roleId) throws Exception {
        RoleResponseDTO roleUser = roleService.getRoleById(roleId);
        return ResponseEntity.ok().body(roleUser);
    }

    /**
     * Delete role by id
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("/role/{roleId}")
    private ResponseEntity<String> deleteRole(@PathVariable("roleId") Long roleId) throws Exception {
        roleService.delete(roleId);
        return new ResponseEntity<>(" Role deleted successfully", HttpStatus.OK);
    }
}
