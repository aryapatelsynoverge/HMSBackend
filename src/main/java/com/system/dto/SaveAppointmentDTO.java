package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppointmentDTO {

    @JsonProperty(value = "appointment_id")
    private Long id;

    @JsonProperty(value = "appointment_custom_id")
    private String appointmentId;

    @JsonProperty(value = "appointment_title")
    private String appointmentTitle;

    @JsonProperty(value = "appointment_detail")
    private String appointmentDetail;

    @JsonProperty(value = "appointment_date")
    @Temporal(TemporalType.DATE)
    private Date appointmentDate;

    @JsonProperty(value = "appointment_time")
    private String appointmentTime;

    @JsonProperty(value = "user_email")
    private String userEmail;

}
