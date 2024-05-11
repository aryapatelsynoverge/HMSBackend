package com.system.model;


import com.system.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "appointment_dtl")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends Auditable<String> implements Serializable {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appointment_custom_id")
    private String appointmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id", referencedColumnName = "id", nullable = false)
    private Hospital hospital;

    @Column(name = "appointment_title")
    private String appointmentTitle;

    @Column(name = "appointment_detail")
    private String appointmentDetail;

    @Column(name = "appointment_date")
    @Temporal(TemporalType.DATE)
    private Date appointmentDate;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @Column(name = "appointment_status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

}
