package com.system.controller;

import com.system.dto.ResponseDTO;
import com.system.dto.LoginRequestDTO;
import com.system.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/loginUser")
public class LoginController {

    private final LoginService loginService;

    /**
     * Login user
     *
     * @param loginRequestDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(loginService.userLogin(loginRequestDTO));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }
}