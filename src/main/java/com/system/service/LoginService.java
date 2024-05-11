package com.system.service;

import com.system.dto.LoginRequestDTO;
import com.system.dto.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO userLogin(LoginRequestDTO loginRequestDTO) throws Exception;

}
