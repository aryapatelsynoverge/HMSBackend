package com.system.service.impl;

import com.system.constant.RoleUser;
import com.system.dto.*;

import com.system.exceptionhandling.*;
import com.system.model.*;
import com.system.service.EmailService;

import org.jasypt.util.text.BasicTextEncryptor;

import com.system.repository.*;
import com.system.service.DoctorService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService, Serializable {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final HospitalRepositary hospitalRepositary;
    private final DoctorHospitalRepository doctorHospitalRepository;
    private final PatientRepository patientRepository;
    private final DoctorPatientHospitalRepository doctorPatientHospitalRepository;
    private final EmailService emailService;
    List<String> hospitalList;

    /**
     * Save or update doctor
     *
     * @param doctorDTO
     * @return
     * @throws Exception
     */
    @Override
    public void saveOrUpdateDoctor(DoctorDTO doctorDTO, String loggedEmail) throws Exception {
        if (doctorDTO.getId() == null) {
            isUserEmailExists(doctorDTO.getEmail());
            User user = new User();
            user.setId(doctorDTO.getUserId());
            user.setName(doctorDTO.getUserName());
            user.setEmail(doctorDTO.getEmail());
            userRepository.save(user);
            hospitalList = doctorDTO.getHospitalId();

            Role role = roleRepository.findById(doctorDTO.getRoleId()).orElseThrow(() -> new UserNotFoundException("Role not found with id : " + doctorDTO.getRoleId()));
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);

            Doctor doctor = convertDTOtoModel(doctorDTO, user);
            doctor = doctorRepository.save(doctor);
            doctor.setDoctorId("DOC-" + doctor.getId());
            Doctor newDoctor = doctorRepository.save(doctor);

            hospitalList.forEach((String hospitalId) -> {
                Long id = Long.parseLong(hospitalId.substring(3));
                Hospital hospital = hospitalRepositary.findById(id)
                        .orElseThrow(() -> new HospitalNotFoundException("Hospital not found" + hospitalId));
                DoctorHospital doctorHospital = new DoctorHospital();
                doctorHospital.setHospital(hospital);
                doctorHospital.setDoctor(newDoctor);
                doctorHospitalRepository.save(doctorHospital);
            });
            emailService.sendSetPasswordLink(doctorDTO.getEmail());
        } else {
            // Update operation
            Doctor existingDoctor = doctorRepository.findById(doctorDTO.getId())
                    .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id : " + doctorDTO.getId()));
            if (!doctorDTO.isActive()) {
                canDoctorBeNotActive(existingDoctor);
            }
            User user = userRepository.findById(existingDoctor.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("User not found" + doctorDTO.getUserId()));
            user.setName(doctorDTO.getUserName());
            user.setEmail(doctorDTO.getEmail());
            userRepository.save(user);

            existingDoctor.setName(doctorDTO.getDoctorName());
            existingDoctor.setAddress(doctorDTO.getAddress());
            existingDoctor.setState(doctorDTO.getState());
            existingDoctor.setCity(doctorDTO.getCity());
            existingDoctor.setZipCode(doctorDTO.getZipCode());
            existingDoctor.setPhoneNumber(doctorDTO.getPhoneNumber());
            existingDoctor.setActive(doctorDTO.isActive());
            doctorRepository.save(existingDoctor);

            List<DoctorHospital> doctorHospitals = doctorHospitalRepository.findByDoctor(existingDoctor);
            doctorHospitals.forEach(doctorHospitalRepository::delete);
            hospitalList = doctorDTO.getHospitalId();
            hospitalList.forEach((String hospitalId) -> {
                Long id = Long.parseLong(hospitalId.substring(3));
                Hospital hospital = hospitalRepositary.findById(id)
                        .orElseThrow(() -> new HospitalNotFoundException("Hospital not found" + id));
                DoctorHospital doctorHospital = new DoctorHospital();
                doctorHospital.setHospital(hospital);
                doctorHospital.setDoctor(existingDoctor);
                doctorHospitalRepository.save(doctorHospital);
            });
        }
    }

    /**
     * Get all doctors
     *
     * @param userEmail
     * @return
     * @throws Exception
     */
    @Override
    public List<AllDoctorResponseDTO> getAllDoctors(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail);
        UserRole userRole = userRoleRepository.findByUser(user);
        String role = userRole.getRole().getRole();
        List<AllDoctorResponseDTO> allDoctorResponseDTOList = new ArrayList<>();
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Doctor> doctorList = doctorRepository.findAll();
            doctorList.forEach((Doctor doctor) ->
                    {
                        AllDoctorResponseDTO allDoctorResponseDTO = new AllDoctorResponseDTO(doctor);
                        allDoctorResponseDTOList.add(allDoctorResponseDTO);
                    }
            );
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            Doctor doctor = doctorRepository.findByUser(user);
            AllDoctorResponseDTO allDoctorResponseDTO = new AllDoctorResponseDTO(doctor);
            allDoctorResponseDTOList.add(allDoctorResponseDTO);
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.PATIENT))) {
            Patient patient = patientRepository.findByUser(user);
            DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(patient);
            AllDoctorResponseDTO allDoctorResponseDTO = new AllDoctorResponseDTO(doctorPatientHospital.getDoctor());
            allDoctorResponseDTOList.add(allDoctorResponseDTO);
        }
        return allDoctorResponseDTOList;
    }

    /**
     * Get doctor by ID
     *
     * @param doctorId
     * @return
     * @throws Exception
     */
    @Override
    public DoctorHospitalDTO getDoctorById(String doctorId) throws DoctorNotFoundException {

        List<HospitalDTO> hospitalList = new ArrayList<>();
        Long id = Long.parseLong(doctorId.substring(4));
        Doctor existingDoctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor not found " + doctorId));
        List<DoctorHospital> doctorHospitalList = doctorHospitalRepository.findByDoctor(existingDoctor);
        doctorHospitalList.forEach((DoctorHospital doctorHospital) -> {
            Hospital hospital = doctorHospital.getHospital();
            HospitalDTO hospitalDTO = new HospitalDTO((hospital));
            hospitalList.add(hospitalDTO);
        });
        DoctorHospitalDTO doctorHospitalDTO = convertModelToDTO(existingDoctor);
        doctorHospitalDTO.setDoctorHospital(hospitalList);
        return doctorHospitalDTO;
    }


    /**
     * Delete doctor by ID
     *
     * @param id
     */
    @Override
    public void delete(Long id) throws Exception {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Doctor not found with id : " + id));
        User user = existingDoctor.getUser();
        UserRole userRole = userRoleRepository.findByUser(user);
        userRoleRepository.delete(userRole);
        userRepository.delete(user);
        doctorRepository.delete(existingDoctor);
    }

    /**
     * Doctor DTO to model
     *
     * @param doctorDTO
     * @param user
     * @return
     */
    private Doctor convertDTOtoModel(DoctorDTO doctorDTO, User user) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getDoctorName());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setState(doctorDTO.getState());
        doctor.setCity(doctorDTO.getCity());
        doctor.setZipCode(doctorDTO.getZipCode());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setActive(doctorDTO.isActive());
        doctor.setUser(user);
        return doctor;
    }

    /**
     * Doctor model to DTO
     *
     * @param doctor
     * @return
     */
    private DoctorHospitalDTO convertModelToDTO(Doctor doctor) {
        return new DoctorHospitalDTO(doctor);
    }


    /**
     * Checking email already exists
     *
     * @param email
     * @throws Exception
     */
    public void isUserEmailExists(String email) throws EntityNotSetNotActiveException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            throw new EmailAlreadyExistException("Email ID already exists");
        }
    }

    /**
     * Check doctor can be soft deleted
     *
     * @param doctor
     * @throws Exception
     */
    public void canDoctorBeNotActive(Doctor doctor) throws EntityNotSetNotActiveException {
        Optional<DoctorPatientHospital> doctorPatientHospital = Optional.ofNullable(doctorPatientHospitalRepository.findFirstByDoctor(doctor));
        if (doctorPatientHospital.isPresent()) {
            throw new EntityNotSetNotActiveException("Doctor cannot be set as Inactive \n Because doctor is connected with other patients");
        }
    }
}
