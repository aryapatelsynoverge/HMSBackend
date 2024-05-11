package com.system.exceptionhandling;

import com.system.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUserNotFoundException(UserNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(104);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = HospitalNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleHospitalNotFoundException(HospitalNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(105);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = DoctorNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleDoctorNotFoundException(DoctorNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(106);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = PatientNotFoundException.class)
    public ResponseEntity<ResponseDTO> handlePatientNotFoundException(PatientNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(107);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = AppointmentNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleAppointmentNotFoundException(AppointmentNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(504);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = EmailAlreadyExistException.class)
    public ResponseEntity<ResponseDTO> handleEmailAlreadyExistException(EmailAlreadyExistException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(704);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = EntityNotSetNotActiveException.class)
    public ResponseEntity<ResponseDTO> handleEntityNotSetNotActiveException(EntityNotSetNotActiveException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(804);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = InvalidOTPException.class)
    public ResponseEntity<ResponseDTO> handleInvalidOTPException(InvalidOTPException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(904);
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDTO> handleEntityNotFoundException(Exception exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleRoleNotFoundException(RoleNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.ok(responseDTO);
    }
}

