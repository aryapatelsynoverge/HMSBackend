package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @JsonProperty(value = "role_id")
    private Long id;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "role_description")
    private String description;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.description = role.getDescription();
    }
}
