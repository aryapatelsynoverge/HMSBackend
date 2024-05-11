package com.system.controller;

import com.system.dto.ResponseDTO;
import com.system.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/resetPassword")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    /**
     * Verify email
     *
     * @param body
     * @return
     * @throws Exception
     */
    @PostMapping("/verifyEmail")
    public ResponseEntity<ResponseDTO> verifyEmail(@RequestBody Map<String, String> body) throws Exception {
        String userEmail = body.get("email");
        ResponseDTO responseDTO = new ResponseDTO();
        resetPasswordService.verifyEmail(userEmail);
        responseDTO.setCode(HttpStatus.ACCEPTED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Verify OTP
     *
     * @param body
     * @param userEmail
     * @return
     * @throws Exception
     */
    @PostMapping("/verifyOtp")
    public ResponseEntity<ResponseDTO> verifyOtp(@RequestBody Map<String, String> body, @RequestHeader("loggedEmail") String userEmail) throws Exception {
        String otp = body.get("otp");
        ResponseDTO responseDTO = new ResponseDTO();
        resetPasswordService.verifyOtp(userEmail, otp);
        responseDTO.setCode(HttpStatus.ACCEPTED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Set new password
     *
     * @param body
     * @param userEmail
     * @return
     * @throws Exception
     */
    @PostMapping("/setPassword")
    public ResponseEntity<ResponseDTO> setPassword(@RequestBody Map<String, String> body, @RequestHeader("loggedEmail") String userEmail) throws Exception {
        String password = body.get("new_password");
        ResponseDTO responseDTO = new ResponseDTO();
        resetPasswordService.setPassword(userEmail, password);
        responseDTO.setCode(HttpStatus.CREATED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Resend OTP
     *
     * @param userEmail
     * @return
     * @throws Exception
     */
    @GetMapping("/resendOtp")
    public ResponseEntity<ResponseDTO> resendOtp(@RequestHeader("loggedEmail") String userEmail) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        resetPasswordService.resendOtp(userEmail);
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }
}
