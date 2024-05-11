package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorHospitalDTO {

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
    private List<HospitalDTO> doctorHospital;

    public DoctorHospitalDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.doctorId = doctor.getDoctorId();
        this.phoneNumber = doctor.getPhoneNumber();
        this.doctorName = doctor.getName();
        this.address = doctor.getAddress();
        this.city = doctor.getCity();
        this.state = doctor.getState();
        this.zipCode = doctor.getZipCode();
        this.isActive = doctor.isActive();
        this.userId = doctor.getUser().getId();
        this.userName = doctor.getUser().getName();
        this.email = doctor.getUser().getEmail();
        this.doctorHospital = new ArrayList<>();

    }
}
