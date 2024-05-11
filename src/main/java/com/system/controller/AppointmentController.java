package com.system.controller;


import com.system.dto.AppointmentDTO;
import com.system.dto.ResponseDTO;
import com.system.dto.SaveAppointmentDTO;
import com.system.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Save or update appointment
     *
     * @param saveAppointmentDTO
     * @return
     */
    @RequestMapping(value = "/appointment", method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<ResponseDTO> saveOrUpdateAppointment(@RequestBody SaveAppointmentDTO saveAppointmentDTO, @RequestHeader("loggedEmail") String loggedEmail) throws Exception {
        appointmentService.saveOrUpdateAppointment(saveAppointmentDTO, loggedEmail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataCreated());
        responseDTO.setCode(HttpStatus.CREATED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get all appointment
     *
     * @param userEmail
     * @return
     */
    @GetMapping("/all")
    private ResponseEntity<ResponseDTO> getAllAppointment(@RequestHeader("loggedEmail") String userEmail) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(appointmentService.getAllAppointment(userEmail));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Getting appointment by ID
     *
     * @param appointmentId
     * @return
     * @throws Exception
     */
    @GetMapping("/appointment/{appointmentId}")
    private ResponseEntity<ResponseDTO> getAppointment(@PathVariable("appointmentId") String appointmentId)
            throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(appointmentService.getAppointmentById(appointmentId));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Deleting the appointment
     *
     * @param appointmentId
     * @return
     */
    @DeleteMapping("/appointment/{appointmentId}")
    private ResponseEntity<String> deleteAppointment(@PathVariable("appointmentId") Long appointmentId) throws Exception {
        appointmentService.delete(appointmentId);
        return new ResponseEntity<>(" Appointment deleted successfully", HttpStatus.OK);
    }


    /**
     * Approving the appointment
     *
     * @param appointmentDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/appointment/approve")
    private ResponseEntity<ResponseDTO> approveAppointment(@RequestBody AppointmentDTO appointmentDTO) throws Exception {
        appointmentService.approveAppointment(appointmentDTO.getId());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode((HttpStatus.OK.value()));
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Rejecting the appointment
     *
     * @param appointmentDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/appointment/reject")
    private ResponseEntity<ResponseDTO> rejectAppointment(@RequestBody AppointmentDTO appointmentDTO) throws Exception {
        appointmentService.rejectAppointment(appointmentDTO.getId());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode((HttpStatus.OK.value()));
        return ResponseEntity.ok().body(responseDTO);
    }

}
