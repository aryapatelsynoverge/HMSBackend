package com.system.repository;

import com.system.model.Patient;
import com.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUser(User user);

}
