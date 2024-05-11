package com.system.service.impl;


import com.system.constant.RoleUser;
import com.system.dto.AllHospitalResponseDTO;
import com.system.dto.HospitalDTO;
import com.system.exceptionhandling.HospitalNotFoundException;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.*;
import com.system.repository.*;
import com.system.service.HospitalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepositary hospitalRepositary;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorHospitalRepository doctorHospitalRepository;
    private final PatientRepository patientRepository;
    private final DoctorPatientHospitalRepository doctorPatientHospitalRepository;

    /**
     * Save or update hospital
     *
     * @param hospitalDTO
     * @return
     * @throws Exception
     */
    @Override
    public void saveOrUpdateHospital(HospitalDTO hospitalDTO, String loggedEmail) throws Exception {

        if (hospitalDTO.getId() == null) {
            Hospital hospital = convertDTOtoModel(hospitalDTO);
            hospital = hospitalRepositary.save(hospital);
            hospital.setHospitalId("HO-" + hospital.getId());
            hospitalRepositary.save(hospital);
        } else {
            Hospital existingHospital = hospitalRepositary.findById(hospitalDTO.getId())
                    .orElseThrow(() -> new HospitalNotFoundException("Hospital not found: " + hospitalDTO.getId()));
            if (!hospitalDTO.isActive()) {
                canHospitalBeNotActive(existingHospital);
            }
            existingHospital.setName(hospitalDTO.getName());
            existingHospital.setAddress(hospitalDTO.getAddress());
            existingHospital.setCity(hospitalDTO.getCity());
            existingHospital.setZipCode(hospitalDTO.getZipCode());
            existingHospital.setHospitalType(HospitalType.valueOf(hospitalDTO.getHospitalType()));
            existingHospital.setActive(hospitalDTO.isActive());
            hospitalRepositary.save(existingHospital);
        }
    }

    /**
     * Get all hospital
     *
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public List<AllHospitalResponseDTO> getAllHospital(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        UserRole userRole = userRoleRepository.findByUser(user);
        String role = userRole.getRole().getRole();
        List<AllHospitalResponseDTO> allHospitalResponseDTOList = new ArrayList<>();
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Hospital> hospitals = new ArrayList<>(hospitalRepositary.findAll());
            hospitals.forEach((Hospital hospital) -> {
                AllHospitalResponseDTO allHospitalResponseDTO = new AllHospitalResponseDTO(hospital);
                allHospitalResponseDTOList.add(allHospitalResponseDTO);
            });
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            Doctor doctor = doctorRepository.findByUser(user);
            List<DoctorHospital> doctorHospitals = doctorHospitalRepository.findByDoctor(doctor);
            doctorHospitals.forEach((DoctorHospital doctorHospital) -> {
                allHospitalResponseDTOList.add(new AllHospitalResponseDTO(doctorHospital.getHospital()));
            });
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.PATIENT))) {
            Patient patient = patientRepository.findByUser(user);
            DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(patient);
            allHospitalResponseDTOList.add(new AllHospitalResponseDTO(doctorPatientHospital.getHospital()));
        }
        return allHospitalResponseDTOList;
    }

    /**
     * Get hospital by ID
     *
     * @param hospitalId
     * @return
     * @throws Exception
     */
    @Override
    public HospitalDTO getHospitalById(String hospitalId) throws HospitalNotFoundException {
        Long id = Long.parseLong(hospitalId.substring(3));
        Hospital existingHospital = hospitalRepositary.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException("Hospital not found " + hospitalId));
        return convertModelToDTO(existingHospital);
    }


    @Override
    public Map<String, Long> getHospitalPatient(String userEmail, String userRole) throws Exception {
        Map<String, Long> hospitalPatientCountMap = new HashMap<>();
        if (userRole.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Hospital> hospitalList = hospitalRepositary.findAll();
            hospitalList.forEach(hospital -> {
                long count = doctorPatientHospitalRepository.countByHospital(hospital);
                hospitalPatientCountMap.put(hospital.getName(), count);
            });
        } else if (userRole.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            User user = userRepository.findByEmail(userEmail);
            Doctor doctor = doctorRepository.findByUser(user);
            List<DoctorHospital> doctorHospitalList = doctorHospitalRepository.findByDoctor(doctor);
            doctorHospitalList.forEach(doctorHospital -> {
                long count = doctorPatientHospitalRepository.countByHospitalAndDoctor(doctorHospital.getHospital(), doctor);
                hospitalPatientCountMap.put(doctorHospital.getHospital().getName(), count);
            });
        }
        return hospitalPatientCountMap;
    }

    @Override
    public Map<String, Long> getHospitalDoctor(String userEmail, String userRole) throws Exception {
        Map<String, Long> hospitalDoctorCountMap = new HashMap<>();
        if (userRole.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Hospital> hospitalList = hospitalRepositary.findAll();
            hospitalList.forEach(hospital -> {
                long count = doctorHospitalRepository.countByHospital(hospital);
                hospitalDoctorCountMap.put(hospital.getName(), count);
            });
        } else if (userRole.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            User user = userRepository.findByEmail(userEmail);
            Doctor doctor = doctorRepository.findByUser(user);
            List<DoctorHospital> doctorHospitalList = doctorHospitalRepository.findByDoctor(doctor);
            doctorHospitalList.forEach(doctorHospital -> {
                long count = doctorHospitalRepository.countByHospitalAndDoctor(doctorHospital.getHospital(), doctor);
                hospitalDoctorCountMap.put(doctorHospital.getHospital().getName(), count);
            });
        }
        return hospitalDoctorCountMap;
    }


    /**
     * Delete hospital by ID
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Long id) throws Exception {
        Hospital existingHospital = hospitalRepositary.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Hospital not found with id : " + id));
        hospitalRepositary.delete(existingHospital);
    }


    /**
     * DTO to model
     *
     * @param hospitalDTO
     * @return
     */
    private Hospital convertDTOtoModel(HospitalDTO hospitalDTO) {

        Hospital hospital = new Hospital();
        hospital.setId(hospitalDTO.getId());
        hospital.setName(hospitalDTO.getName());
        hospital.setAddress(hospitalDTO.getAddress());
        hospital.setCity(hospitalDTO.getCity());
        hospital.setState(hospitalDTO.getState());
        hospital.setZipCode(hospitalDTO.getZipCode());
        hospital.setHospitalType(HospitalType.valueOf(hospitalDTO.getHospitalType()));
        hospital.setActive(hospitalDTO.isActive());
        return hospital;
    }

    /**
     * Model to DTO
     *
     * @param hospital
     * @return
     */
    private HospitalDTO convertModelToDTO(Hospital hospital) {
        return new HospitalDTO(hospital);
    }

    public void canHospitalBeNotActive(Hospital hospital) throws Exception {
        Optional<DoctorHospital> doctorHospital = Optional.ofNullable(doctorHospitalRepository.findFirstByHospital(hospital));
        if (doctorHospital.isPresent()) {
            throw new Exception("Hospital cannot be set as not active");
        }
    }
}
