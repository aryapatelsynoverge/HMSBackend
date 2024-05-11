package com.system.controller;

import com.system.dto.ResponseDTO;
import com.system.dto.UserDTO;
import com.system.model.User;
import com.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    /**
     * adding or updating user
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseDTO> editUser(@RequestBody UserDTO userDTO) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        userService.editUser(userDTO);
        responseDTO.setMessage(responseDTO.dataCreated());
        responseDTO.setCode(HttpStatus.ACCEPTED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get user by Id
     *
     * @param loggedEmail
     * @return
     * @throws Exception
     */
    @GetMapping("/getUser")
    private ResponseEntity<ResponseDTO> getUser(@RequestHeader("loggedEmail") String loggedEmail) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(userService.getUserByEmail(loggedEmail));
        responseDTO.setMessage(responseDTO.dataRetrived());
        responseDTO.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Set new password
     * @param body
     * @param userEmail
     * @return
     * @throws Exception
     */
    @PostMapping("/setPassword")
    public ResponseEntity<ResponseDTO> setPassword(@RequestBody Map<String, String> body, @RequestHeader("loggedEmail") String userEmail) throws Exception {
        String currentPassword = body.get("current_password");
        String newPassword = body.get("new_password");
        ResponseDTO responseDTO = new ResponseDTO();
        userService.setPassword(userEmail, currentPassword, newPassword);
        responseDTO.setCode(HttpStatus.CREATED.value());
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Delete user by id
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/user/{userId}")
    private ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) throws Exception {
        userService.delete(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
