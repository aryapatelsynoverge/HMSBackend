package com.system.controller;

import com.system.dto.*;
import com.system.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * Save or update doctor details
     *
     * @param doctorDTO
     * @return
     */
    @RequestMapping(value = "/doctor", method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<ResponseDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO, @RequestHeader("loggedEmail") String loggedEmail) throws Exception {
        doctorService.saveOrUpdateDoctor(doctorDTO, loggedEmail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataCreated());
        if (doctorDTO.getId() == null) {
            responseDTO.setCode(HttpStatus.CREATED.value());
        } else {
            responseDTO.setCode(HttpStatus.ACCEPTED.value());
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get all doctors
     *
     * @param userEmail
     * @return
     */
    @GetMapping("/all")
    private ResponseEntity<ResponseDTO> getAllDoctors(@RequestHeader("loggedEmail") String userEmail) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(doctorService.getAllDoctors(userEmail));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get doctor by ID
     *
     * @param doctorId
     * @return
     * @throws Exception
     */
    @GetMapping("/doctor/{doctorId}")
    private ResponseEntity<ResponseDTO> getDoctor(@PathVariable("doctorId") String doctorId) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(doctorService.getDoctorById(doctorId));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Delete doctor by id
     *
     * @param doctorId
     * @return
     */
    @DeleteMapping("/doctor/{doctorId}")
    private ResponseEntity<String> deleteDoctor(@PathVariable("doctorId") Long doctorId) throws Exception {
        doctorService.delete(doctorId);
        return new ResponseEntity<>(" Doctor deleted successfully", HttpStatus.OK);
    }

}
