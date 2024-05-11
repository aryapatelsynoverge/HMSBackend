package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty(value = "user_id")
    private Long id;

    @JsonProperty(value = "user_name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName(); // Assuming User has a getName() method
        this.email = user.getEmail();
    }
}

