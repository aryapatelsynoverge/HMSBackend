package com.system.service;

import com.system.dto.AllDoctorResponseDTO;
import com.system.dto.DoctorDTO;
import com.system.dto.DoctorHospitalDTO;

import java.util.List;

public interface DoctorService {
    void saveOrUpdateDoctor(DoctorDTO doctorDTO, String loggedEmail) throws Exception;

    List<AllDoctorResponseDTO> getAllDoctors(String userEmail) throws Exception;

    DoctorHospitalDTO getDoctorById(String doctorId) throws Exception;

    void delete(Long id) throws Exception;
}
