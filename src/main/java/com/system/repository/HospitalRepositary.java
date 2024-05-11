package com.system.repository;

import com.system.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepositary extends JpaRepository<Hospital, Long> {
    Hospital findByHospitalId(String hospitalId);
}
