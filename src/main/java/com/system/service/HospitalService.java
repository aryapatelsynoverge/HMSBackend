package com.system.service;

import com.system.dto.AllHospitalResponseDTO;
import com.system.dto.HospitalDTO;
import java.util.List;
import java.util.Map;

public interface HospitalService {

    void saveOrUpdateHospital(HospitalDTO hospitalDTO, String loggedEmail) throws Exception;

    List<AllHospitalResponseDTO> getAllHospital(String email) throws Exception;

    HospitalDTO getHospitalById(String hospitalId) throws Exception;

    Map<String, Long> getHospitalPatient(String userEmail, String userRole) throws Exception;

    Map<String, Long> getHospitalDoctor(String userEmail, String userRole) throws Exception;

    void delete(Long id) throws Exception;

}
