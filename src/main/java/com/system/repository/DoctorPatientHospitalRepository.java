package com.system.repository;

import com.system.model.Doctor;
import com.system.model.DoctorPatientHospital;
import com.system.model.Hospital;
import com.system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorPatientHospitalRepository extends JpaRepository<DoctorPatientHospital, Long> {
    DoctorPatientHospital findByPatient(Patient patient);
    List<DoctorPatientHospital> findByDoctor(Doctor doctor);
    DoctorPatientHospital findFirstByDoctor(Doctor doctor);
    DoctorPatientHospital findByDoctorAndPatient(Doctor doctor, Patient patient);
    Long countByHospital(Hospital hospital);
    Long countByHospitalAndDoctor(Hospital hospital, Doctor doctor);
}
