package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {

    @JsonProperty(value = "role_id")
    private Long id;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "role_description")
    private String description;

    @JsonProperty(value = "Users")
    private List<User> userList;
}
