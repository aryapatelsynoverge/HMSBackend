package com.system.repository;

import com.system.model.Doctor;
import com.system.model.Hospital;
import com.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByUser(User user);
}
