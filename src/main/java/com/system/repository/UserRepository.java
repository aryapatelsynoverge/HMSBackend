package com.system.repository;

import com.system.model.DoctorPatientHospital;
import com.system.model.Patient;
import com.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
