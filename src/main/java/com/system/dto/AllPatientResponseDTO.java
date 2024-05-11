package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.system.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllPatientResponseDTO {

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

    @JsonProperty(value = "doctor_custom_id")
    private String doctorId;

    @JsonProperty(value = "doctor_name")
    private String doctorName;

    @JsonProperty(value = "hospital_custom_id")
    private String hospitalId;

    @JsonProperty(value = "hospital_name")
    private String hospitalName;


    /**
     * All patient details
     *
     * @param patient
     */
    public AllPatientResponseDTO(Patient patient) {


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

    }
}
