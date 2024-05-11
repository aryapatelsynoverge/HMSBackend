package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    @JsonProperty(value = "role_id")
    private final Long roleId = 3L;

    @JsonProperty(value = "patient_id")
    private Long id;

    @JsonProperty(value = "patient_custom_id")
    private String patientId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "age")
    private int age;

    @JsonProperty(value = "blood_group")
    private String bloodGroup;

    @JsonProperty(value = "phone_number")
    private Long phoneNumber;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "zipcode")
    private int zipcode;

    @JsonProperty(value = "is_active")
    private boolean isActive;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "doctor_email")
    private String doctorEmail;

    @JsonProperty(value = "selected_hospital")
    private List<String> hospitalId;

    @JsonProperty(value = "documents")
    private List<DocumentDTO> documentDTOList;

}
