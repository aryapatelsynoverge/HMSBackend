package com.system.service;

import com.system.dto.PatientByIdDTO;
import com.system.dto.PatientDTO;
import com.system.dto.AllPatientResponseDTO;
import com.system.exceptionhandling.PatientNotFoundException;

import java.util.List;

public interface PatientService {

    void saveOrUpdatePatient(PatientDTO patientDTO, String loggedRole, String loggedEmail) throws Exception;

    List<AllPatientResponseDTO> getAllPatient(String userEmail) throws Exception;

    PatientByIdDTO getPatientById(String patientId) throws PatientNotFoundException;

    void delete(Long id) throws PatientNotFoundException;

}
