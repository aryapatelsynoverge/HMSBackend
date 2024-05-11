package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    @JsonProperty(value = "role_id")
    private final Long roleId = 2L;

    @JsonProperty(value = "doctor_id")
    private Long id;

    @JsonProperty(value = "doctor_name")
    private String doctorName;

    @JsonProperty(value = "phone_number")
    private Long phoneNumber;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "zipcode")
    private int zipCode;

    @JsonProperty(value = "is_active")
    private boolean isActive;

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "doctor_custom_id")
    private String doctorId;

    @JsonProperty(value = "selected_hospital")
    private List<String> hospitalId;

    public boolean isActive() {
        return isActive;
    }

}
