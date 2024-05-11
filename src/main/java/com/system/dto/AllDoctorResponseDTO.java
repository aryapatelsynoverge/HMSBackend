package com.system.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDoctorResponseDTO {
    @JsonProperty(value = "doctor_custom_id")
    private String doctorId;

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


    /**
     * All doctor details
     * @param doctor
     */
    public AllDoctorResponseDTO(Doctor doctor) {
        this.doctorId = doctor.getDoctorId();
        this.doctorName = doctor.getName();
        this.phoneNumber = doctor.getPhoneNumber();
        this.address = doctor.getAddress();
        this.city = doctor.getCity();
        this.state = doctor.getState();
        this.zipCode = doctor.getZipCode();
        this.isActive = doctor.isActive();
    }
}
