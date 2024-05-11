package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllHospitalResponseDTO {
    @JsonProperty(value = "hospital_id")
    private Long id;

    @JsonProperty(value = "hospital_custom_id")
    private String hospitalId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "zipcode")
    private int zipCode;

    @JsonProperty(value = "hospital_type")
    private String hospitalType;

    @JsonProperty(value = "is_active")
    private boolean isActive;


    /**
     * All hospital details
     * @param hospital
     */
    public AllHospitalResponseDTO(Hospital hospital) {
        this.id=hospital.getId();
        this.name=hospital.getName();
        this.address=hospital.getAddress();
        this.city=hospital.getCity();
        this.state=hospital.getState();
        this.zipCode=hospital.getZipCode();
        this.hospitalType= String.valueOf(hospital.getHospitalType());
        this.isActive=hospital.isActive();
        this.hospitalId=hospital.getHospitalId();
    }
}
