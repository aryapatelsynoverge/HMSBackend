package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Hospital;
import com.system.model.Patient;
import com.system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientByIdDTO {

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

    @JsonProperty(value = "selected_hospital")
    private List<HospitalDTO> hospitalId;

    @JsonProperty(value = "documents")
    private List<DocumentDTO> documents;


    /**
     * Get patient by ID DTO
     * @param patient
     */
    public PatientByIdDTO(Patient patient) {

        this.id = patient.getId();
        this.patientId = patient.getPatientId();
        this.name = patient.getName();
        this.address = patient.getAddress();
        this.age = patient.getAge();
        this.bloodGroup = patient.getBloodGroup();
        this.phoneNumber = patient.getPhoneNumber();
        this.zipcode = patient.getZipcode();
        this.state = patient.getState();
        this.city = patient.getCity();
        this.isActive = patient.isActive();
        this.userName = patient.getUser().getName();
        this.email = patient.getUser().getEmail();
        this.hospitalId = new ArrayList<>();
        this.documents = new ArrayList<>();
    }
}
