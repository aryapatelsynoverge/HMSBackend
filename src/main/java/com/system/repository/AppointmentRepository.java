package com.system.repository;

import com.system.model.Appointment;
import com.system.model.Doctor;
import com.system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    Appointment findLastByPatient(Patient patient);

}
