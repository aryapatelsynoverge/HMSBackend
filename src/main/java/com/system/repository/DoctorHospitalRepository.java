package com.system.repository;

import com.system.model.Doctor;
import com.system.model.DoctorHospital;
import com.system.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorHospitalRepository extends JpaRepository<DoctorHospital, Long> {
    List<DoctorHospital> findByDoctor(Doctor doctor);
    DoctorHospital findFirstByHospital(Hospital hospital);
    Long countByHospital(Hospital hospital);
    Long countByHospitalAndDoctor(Hospital hospital, Doctor doctor);
}
