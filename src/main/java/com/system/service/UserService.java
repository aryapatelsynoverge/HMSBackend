package com.system.service;

import com.system.dto.UserDTO;

public interface UserService {

    void editUser(UserDTO userDTO) throws Exception;

    UserDTO getUserByEmail(String loggedEmail) throws Exception;

    void delete(long id) throws Exception;

    void setPassword(String userEmail, String currentPassword, String newPassword) throws Exception;
}
