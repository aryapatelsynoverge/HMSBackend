package com.system.service;

import com.system.dto.AllAppointmentResponseDTO;
import com.system.dto.SaveAppointmentDTO;
import com.system.exceptionhandling.AppointmentNotFoundException;

import java.util.List;

public interface AppointmentService {

    void saveOrUpdateAppointment(SaveAppointmentDTO saveAppointmentDTO, String loggedEmail) throws Exception;

    List<AllAppointmentResponseDTO> getAllAppointment(String userEmail) throws Exception;

    SaveAppointmentDTO getAppointmentById(String appointmentId) throws Exception;

    void delete(Long id) throws Exception;

    void approveAppointment(Long id) throws Exception;

    void rejectAppointment(Long id) throws Exception;
}
