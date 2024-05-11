package com.system.controller;


import com.system.dto.ResponseDTO;
import com.system.dto.HospitalDTO;
import com.system.service.HospitalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    /**
     * Save or update hospital
     *
     * @param hospitalDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hospital", method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<ResponseDTO> saveHospital(@RequestBody HospitalDTO hospitalDTO, @RequestHeader("loggedEmail") String loggedEmail) throws Exception {
        hospitalService.saveOrUpdateHospital(hospitalDTO, loggedEmail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(responseDTO.dataCreated());
        if (hospitalDTO.getId() == null) {
            responseDTO.setCode(HttpStatus.CREATED.value());
        } else {
            responseDTO.setCode(HttpStatus.ACCEPTED.value());
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get all hospital
     *
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping("/all")
    private ResponseEntity<ResponseDTO> getAllHospital(@RequestHeader("loggedEmail") String email) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(hospitalService.getAllHospital(email));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get hospital By ID
     *
     * @param hospitalId
     * @return
     * @throws Exception
     */
    @GetMapping("/hospital/{hospitalId}")
    private ResponseEntity<ResponseDTO> getHospital(@PathVariable("hospitalId") String hospitalId) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(hospitalService.getHospitalById(hospitalId));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get hospital-patient data
     *
     * @param userEmail
     * @param userRole
     * @return
     * @throws Exception
     */
    @GetMapping("/getHospitalPatient")
    public ResponseEntity<ResponseDTO> getHospitalPatient(@RequestHeader("loggedEmail") String userEmail, @RequestHeader("loggedRole") String userRole) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(hospitalService.getHospitalPatient(userEmail, userRole));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get hospital-doctor data
     *
     * @param userEmail
     * @param userRole
     * @return
     * @throws Exception
     */
    @GetMapping("/getHospitalDoctor")
    public ResponseEntity<ResponseDTO> getHospitalDoctor(@RequestHeader("loggedEmail") String userEmail, @RequestHeader("loggedRole") String userRole) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(hospitalService.getHospitalDoctor(userEmail, userRole));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Delete hospital by ID
     *
     * @param hospitalId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/hospital/{hospitalId}")
    private ResponseEntity<String> deleteHospital(@PathVariable("hospitalId") Long hospitalId) throws Exception {
        hospitalService.delete(hospitalId);
        return new ResponseEntity<>(" Hospital deleted successfully", HttpStatus.OK);
    }

}
