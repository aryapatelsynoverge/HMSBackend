package com.system.service.impl;

import com.system.constant.RoleUser;
import com.system.dto.AllAppointmentResponseDTO;
import com.system.dto.SaveAppointmentDTO;
import com.system.exceptionhandling.AppointmentNotFoundException;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.*;
import com.system.repository.*;
import com.system.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorPatientHospitalRepository doctorPatientHospitalRepository;
    private final UserRoleRepository userRoleRepository;
    private final DoctorRepository doctorRepository;

    /**
     * Save or update appointment
     *
     * @param saveAppointmentDTO
     * @return
     */
    @Override
    public void saveOrUpdateAppointment(SaveAppointmentDTO saveAppointmentDTO, String loggedEmail) throws AppointmentNotFoundException, Exception {
        User user = userRepository.findByEmail(saveAppointmentDTO.getUserEmail());
        if (saveAppointmentDTO.getId() == null) {
            Patient patient = patientRepository.findByUser(user);
            DoctorPatientHospital doctorPatientHospital = doctorPatientHospitalRepository.findByPatient(patient);
            Doctor doctor = doctorPatientHospital.getDoctor();
            Hospital hospital = doctorPatientHospital.getHospital();

            if (isDoctorAvailable(doctor, saveAppointmentDTO)) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentTitle(saveAppointmentDTO.getAppointmentTitle());
                appointment.setAppointmentDetail(saveAppointmentDTO.getAppointmentDetail());
                appointment.setAppointmentDate(saveAppointmentDTO.getAppointmentDate());
                appointment.setAppointmentTime(saveAppointmentDTO.getAppointmentTime());
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                appointment.setHospital(hospital);
                appointment = appointmentRepository.save(appointment);
                appointment.setAppointmentId("APT-" + appointment.getId());
                appointmentRepository.save(appointment);
            }
        } else {
            Appointment existingAppointment = appointmentRepository.findById(saveAppointmentDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found " + saveAppointmentDTO.getId()));
            Doctor doctor = existingAppointment.getDoctor();
            if ((!existingAppointment.getAppointmentDate().equals(saveAppointmentDTO.getAppointmentDate())
                    || !existingAppointment.getAppointmentTime().equals(saveAppointmentDTO.getAppointmentTime()))
                    && (!isDoctorAvailable(doctor, saveAppointmentDTO))) {
                throw new Exception("Doctor is not available at this date and time");
            } else {
                existingAppointment.setAppointmentDate(saveAppointmentDTO.getAppointmentDate());
                existingAppointment.setAppointmentTime(saveAppointmentDTO.getAppointmentTime());
                existingAppointment.setAppointmentTitle(saveAppointmentDTO.getAppointmentTitle());
                existingAppointment.setAppointmentDetail(saveAppointmentDTO.getAppointmentDetail());
                appointmentRepository.save(existingAppointment);
            }
        }
    }
    /**
     * Get all appointments
     *
     * @param userEmail
     * @return
     */
    @Override
    public List<AllAppointmentResponseDTO> getAllAppointment(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail);
        UserRole userRole = userRoleRepository.findByUser(user);
        String role = userRole.getRole().getRole();
        List<AllAppointmentResponseDTO> allAppointmentResponseDTOList = new ArrayList<>();
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.ADMIN))) {
            List<Appointment> appointmentList = appointmentRepository.findAll();
            appointmentList.forEach((Appointment appointment) -> {
                allAppointmentResponseDTOList.add(convertModelToDTO(appointment));
            });
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.DOCTOR))) {
            Doctor doctor = doctorRepository.findByUser(user);
            List<Appointment> appointmentList = appointmentRepository.findByDoctor(doctor);
            appointmentList.forEach((Appointment appointment) -> {
                allAppointmentResponseDTOList.add(convertModelToDTO(appointment));
            });
        }
        if (role.equalsIgnoreCase(String.valueOf(RoleUser.PATIENT))) {
            Patient patient = patientRepository.findByUser(user);
            List<Appointment> appointmentList = appointmentRepository.findByPatient(patient);
            appointmentList.forEach((Appointment appointment) -> {
                allAppointmentResponseDTOList.add(convertModelToDTO(appointment));
            });
        }
        return allAppointmentResponseDTOList;
    }
    /**
     * Get appointment by ID
     *
     * @param appointmentId
     * @return
     * @throws Exception
     */
    @Override
    public SaveAppointmentDTO getAppointmentById(String appointmentId) throws AppointmentNotFoundException {
        Long id = Long.parseLong(appointmentId.substring(4));
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found" + appointmentId));
        SaveAppointmentDTO saveAppointmentDTO = new SaveAppointmentDTO();
        saveAppointmentDTO.setId(appointment.getId());
        saveAppointmentDTO.setAppointmentId(appointment.getAppointmentId());
        saveAppointmentDTO.setAppointmentTitle(appointment.getAppointmentTitle());
        saveAppointmentDTO.setAppointmentDetail(appointment.getAppointmentDetail());
        saveAppointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
        saveAppointmentDTO.setAppointmentTime(appointment.getAppointmentTime());
        return saveAppointmentDTO;
    }

    /**
     * Delete particular appointment
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Appointment not found with id : " + id));
        appointmentRepository.delete(existingAppointment);
    }


    /**
     * Approve appointment
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public void approveAppointment(Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found" + id));
        appointment.setStatus(Status.APPROVED);
        appointmentRepository.save(appointment);
    }

    /**
     * Reject appointment
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public void rejectAppointment(Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found" + id));
        appointment.setStatus(Status.REJECTED);
        appointmentRepository.save(appointment);
    }

    /**
     * Checking doctor availability
     *
     * @param doctor
     * @param saveAppointmentDTO
     * @return
     */
    private boolean isDoctorAvailable(Doctor doctor, SaveAppointmentDTO saveAppointmentDTO) {
        List<Appointment> doctorAppointment = appointmentRepository.findByDoctor(doctor);
        int count = 0;
        for (Appointment appointment : doctorAppointment) {
            if (appointment.getAppointmentDate().equals(saveAppointmentDTO.getAppointmentDate()) &&
                    appointment.getAppointmentTime().equals(saveAppointmentDTO.getAppointmentTime())) {
                count++;
            }
        }
        return count < 3;
    }

    /**
     * convert appointment model to dto
     *
     * @param appointment
     * @return
     */
    private AllAppointmentResponseDTO convertModelToDTO(Appointment appointment) {
        return new AllAppointmentResponseDTO(appointment);
    }
}
