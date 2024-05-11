package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.model.*;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAppointmentResponseDTO {

    @JsonProperty(value = "appointment_id")
    private Long id;

    @JsonProperty(value = "patient_custom_id")
    private String patientId;

    @JsonProperty(value = "patient_name")
    private String patientName;

    @JsonProperty(value = "doctor_id")
    private String doctorId;

    @JsonProperty(value = "doctor_name")
    private String doctorName;

    @JsonProperty(value = "hospital_id")
    private String hospitalId;

    @JsonProperty(value = "hospital_name")
    private String hospitalName;

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

    @JsonProperty(value = "status")
    private String status;

    /**
     * For appointment details
     *
     * @param appointment
     */
    public AllAppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.appointmentId = appointment.getAppointmentId();
        this.patientId = appointment.getPatient().getPatientId();
        this.patientName = appointment.getPatient().getName();
        this.doctorId = appointment.getDoctor().getDoctorId();
        this.doctorName = appointment.getDoctor().getName();
        this.hospitalId = appointment.getHospital().getHospitalId();
        this.hospitalName = appointment.getHospital().getName();
        this.appointmentTitle = appointment.getAppointmentTitle();
        this.appointmentDetail = appointment.getAppointmentDetail();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = String.valueOf(appointment.getStatus());
    }
}
