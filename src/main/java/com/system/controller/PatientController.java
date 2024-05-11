package com.system.controller;

import com.system.dto.*;
import com.system.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/patients")
public class PatientController {

    private final PatientService patientService;

    /**
     * Add or update patient
     *
     * @param patientDTO
     * @return
     */
    @RequestMapping(value = "/patient", method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<ResponseDTO> saveOrUpdatePatient(@RequestBody PatientDTO patientDTO, @RequestHeader("loggedRole") String loggedRole, @RequestHeader("loggedEmail") String loggedEmail) throws Exception {
        patientService.saveOrUpdatePatient(patientDTO, loggedRole, loggedEmail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataCreated());
        if (patientDTO.getId() == null) {
            responseDTO.setCode(HttpStatus.CREATED.value());
        } else {
            responseDTO.setCode(HttpStatus.ACCEPTED.value());
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get all patients
     *
     * @return
     */
    @GetMapping("/all")
    private ResponseEntity<ResponseDTO> getAllPatients(@RequestHeader("loggedEmail") String userEmail)
            throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(patientService.getAllPatient(userEmail));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get patient by id
     *
     * @param patientId
     * @return
     * @throws Exception
     */
    @GetMapping("/patient/{patientId}")
    private ResponseEntity<ResponseDTO> getPatient(@PathVariable("patientId") String patientId) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(patientService.getPatientById(patientId));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Delete a patient
     *
     * @param patientId
     * @return
     */
    @DeleteMapping("/patient/{patientId}")
    private ResponseEntity<ResponseDTO> deletePatient(@PathVariable("patientId") Long patientId) throws Exception {
        patientService.delete(patientId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(responseDTO);
    }
}
