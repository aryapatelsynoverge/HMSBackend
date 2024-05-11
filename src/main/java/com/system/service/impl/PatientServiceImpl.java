package com.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.system.dto.*;
import com.system.exceptionhandling.*;

import com.system.model.*;
import com.system.repository.*;
import com.system.service.PatientService;

import com.system.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.system.constant.RoleUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorPatientHospitalRepository doctorPatientHospitalRepository;
    private final DoctorRepository doctorRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final HospitalRepositary hospitalRepositary;
    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;
    List<String> hospitalList;

    /**
     * Save or update patient
     *
     * @param patientDTO
     * @return
     * @throws Exception
     */
    @Override
    public void saveOrUpdatePatient(PatientDTO patientDTO, String loggedRole, String loggedEmail) throws Exception {
        if (patientDTO.getId() == null) {
            isUserEmailExists(patientDTO.getEmail());
            User user = new User();
            user.setName(patientDTO.getUserName());
            user.setEmail(patientDTO.getEmail());
            userRepository.save(user);

            User doctorUser = userRepository.findByEmail(patientDTO.getDoctorEmail());
            Doctor doctor = doctorRepository.findByUser(doctorUser);
            hospitalList = patientDTO.getHospitalId();
            Role role = roleRepository.findById(patientDTO.getRoleId())
                    .orElseThrow(() -> new RoleNotFoundException("No such role found"));
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);

            Patient patient = convertDTOtoModel(patientDTO);
            patient.setUser(user);
            patient.setDocuments(convertStringToJson(patientDTO.getDocumentDTOList()));
            patient = patientRepository.save(patient);
            patient.setPatientId("PAT-" + patient.getId());
            patientRepository.save(patient);

            for (String hospitalId : hospitalList) {
                Long id = Long.parseLong(hospitalId.substring(3));
                Hospital hospital = hospitalRepositary.findById(id)
                        .orElseThrow(() -> new HospitalNotFoundException("Hospital not found" + id));
                DoctorPatientHospital doctorPatientHospital = new DoctorPatientHospital();
                doctorPatientHospital.setDoctor(doctor);
                doctorPatientHospital.setPatient(patient);
                doctorPatientHospital.setHospital(hospital);
                doctorPatientHospitalRepository.save(doctorPatientHospital);
            }
            emailService.sendSetPasswordLink(patientDTO.getEmail());

        } else {
            Patient existingPatient = patientRepository.findById(patientDTO.getId())
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found with id : " + patientDTO.getId()));
            if (!patientDTO.isActive()) {
                canPatientNotActive(existingPatient);
            }
            User user = userRepository.findById(existingPatient.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("User Not found" + existingPatient.getUser().getId()));
            user.setName(patientDTO.getUserName());
            user.setEmail(patientDTO.getEmail());
            userRepository.save(user);

            existingPatient.setName(patientDTO.getName());
            existingPatient.setAge(patientDTO.getAge());
            existingPatient.setBloodGroup(patientDTO.getBloodGroup());
            existingPatient.setAddress(patientDTO.getAddress());
            existingPatient.setCity(patientDTO.getCity());
            existingPatient.setState(patientDTO.getState());
            existingPatient.setZipcode(patientDTO.getZipcode());
            existingPatient.setPhoneNumber(patientDTO.getPhoneNumber());
            existingPatient.setActive(patientDTO.isActive());
            if (loggedRole.equalsIgnoreCase(String.valueOf(RoleUser.PATIENT))) {
                existingPatient.setDocuments(convertStringToJson(patientDTO.getDocumentDTOList()));
            }
            patientRepository.save(existingPatient);

            User loginUser = userRepository.findByEmail(patientDTO.getDoctorEmail());
            UserRole userRole = userRoleRepository.findByUser(loginUser);
            String role = userRole.getRole().getRole();

            if (role.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
                Doctor existingDoctor = doctorRepository.findByUser(loginUser);
                DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository
                        .findByDoctorAndPatient(existingDoctor, existingPatient);
                doctorPatientHospitalRepository.delete(doctorPatientHospital);
                hospitalList = patientDTO.getHospitalId();

                for (String hospitalId : hospitalList) {
                    Hospital hospital = hospitalRepositary.findByHospitalId(hospitalId);
                    DoctorPatientHospital newDoctorPatientHospital = new DoctorPatientHospital();
                    newDoctorPatientHospital.setDoctor(existingDoctor);
                    newDoctorPatientHospital.setPatient(existingPatient);
                    newDoctorPatientHospital.setHospital(hospital);
                    doctorPatientHospitalRepository.save(newDoctorPatientHospital);
                }
            }
        }
    }


    /**
     * Get all patient
     *
     * @param userEmail
     * @return
     * @throws Exception
     */
    @Override
    public List<AllPatientResponseDTO> getAllPatient(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail);
        UserRole userRole = userRoleRepository.findByUser(user);
        String role = userRole.getRole().getRole();
        List<AllPatientResponseDTO> allPatientResponseDTOList = new ArrayList<>();

        if (role.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Patient> patients = patientRepository.findAll();
            for (Patient patient : patients) {
                DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(patient);
                AllPatientResponseDTO allPatientResponseDTO = new AllPatientResponseDTO(patient);
                allPatientResponseDTO.setDoctorId(doctorPatientHospital.getDoctor().getDoctorId());
                allPatientResponseDTO.setDoctorName(doctorPatientHospital.getDoctor().getName());
                allPatientResponseDTO.setHospitalId(doctorPatientHospital.getHospital().getHospitalId());
                allPatientResponseDTO.setHospitalName(doctorPatientHospital.getHospital().getName());
                allPatientResponseDTOList.add(allPatientResponseDTO);

            }
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            Doctor doctor = doctorRepository.findByUser(user);
            List<DoctorPatientHospital> doctorPatientHospitals = doctorPatientHospitalRepository.findByDoctor(doctor);
            doctorPatientHospitals.forEach((DoctorPatientHospital doctorPatientHospital) ->
            {
                AllPatientResponseDTO allPatientResponseDTO = new AllPatientResponseDTO(doctorPatientHospital.getPatient());
                allPatientResponseDTO.setDoctorId(doctorPatientHospital.getDoctor().getDoctorId());
                allPatientResponseDTO.setDoctorName(doctorPatientHospital.getDoctor().getName());
                allPatientResponseDTO.setHospitalId(doctorPatientHospital.getHospital().getHospitalId());
                allPatientResponseDTO.setHospitalName(doctorPatientHospital.getHospital().getName());
                allPatientResponseDTOList.add(allPatientResponseDTO);

            });

        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.PATIENT))) {
            Patient patient = patientRepository.findByUser(user);
            DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(patient);
            AllPatientResponseDTO allPatientResponseDTO = new AllPatientResponseDTO(doctorPatientHospital.getPatient());
            allPatientResponseDTO.setDoctorId(doctorPatientHospital.getDoctor().getDoctorId());
            allPatientResponseDTO.setDoctorName(doctorPatientHospital.getDoctor().getName());
            allPatientResponseDTO.setHospitalId(doctorPatientHospital.getHospital().getHospitalId());
            allPatientResponseDTO.setHospitalName(doctorPatientHospital.getHospital().getName());
            allPatientResponseDTOList.add(allPatientResponseDTO);
        }
        return allPatientResponseDTOList;
    }

    /**
     * Get patient by ID
     *
     * @param patientId
     * @return
     * @throws Exception
     */
    @Override
    public PatientByIdDTO getPatientById(String patientId) throws PatientNotFoundException {

        List<HospitalDTO> hospitalList = new ArrayList<>();
        Long id = Long.parseLong(patientId.substring(4));
        Patient existingPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient does not exist" + id));
        PatientByIdDTO patientByIdDTO = new PatientByIdDTO(existingPatient);
        DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(existingPatient);
        hospitalList.add(new HospitalDTO(doctorPatientHospital.getHospital()));
        patientByIdDTO.setHospitalId(hospitalList);

        String documentsJson = existingPatient.getDocuments();
        List<DocumentDTO> documents;
        if (!documentsJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                documents = objectMapper.readValue(documentsJson, new TypeReference<List<DocumentDTO>>() {
                });
                patientByIdDTO.setDocuments(documents);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON to documents", e);
            }
        }
        return patientByIdDTO;
    }

    /**
     * Delete the patient
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Long id) throws PatientNotFoundException {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with id : " + id));
        patientRepository.delete(existingPatient);
    }

    /**
     * Converting the DTO to model
     *
     * @param patientDTO
     * @return
     */
    private Patient convertDTOtoModel(PatientDTO patientDTO) throws Exception {

        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setAge(patientDTO.getAge());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setAddress(patientDTO.getAddress());
        patient.setCity(patientDTO.getCity());
        patient.setState(patientDTO.getState());
        patient.setZipcode(patientDTO.getZipcode());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setActive(patientDTO.isActive());
        return patient;
    }

    /**
     * Check email already exists
     *
     * @param email
     * @throws Exception
     */
    public void isUserEmailExists(String email) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            throw new EmailAlreadyExistException("Email ID already exists");
        }
    }

    /**
     * Check patient can be soft deleted
     *
     * @param patient
     * @throws Exception
     */
    public void canPatientNotActive(Patient patient) throws Exception {
        Optional<DoctorPatientHospital> doctorPatientHospital = Optional.ofNullable(doctorPatientHospitalRepository.findByPatient(patient));
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findLastByPatient(patient));
        if (doctorPatientHospital.isPresent() || appointment.isPresent()) {
            throw new EntityNotSetNotActiveException("Patient cannot be set as not active \n Because patient is connected with a doctor");
        }
    }

    /**
     * Convert string to json
     * @param documentDTOList
     * @return
     * @throws Exception
     */
    public String convertStringToJson(List<DocumentDTO> documentDTOList) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String documentsJson;
        try {
            documentsJson = objectMapper.writeValueAsString(documentDTOList);
        } catch (JsonProcessingException e) {
            throw new Exception("Error converting documents to JSON", e);
        }
        return documentsJson;
    }
}
