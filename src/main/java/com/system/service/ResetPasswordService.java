package com.system.service;

public interface ResetPasswordService {

    void verifyEmail(String userEmail) throws Exception;

    void verifyOtp(String userEmail, String otp) throws Exception;

    void setPassword(String userEmail, String password) throws Exception;

    void resendOtp(String userEmail) throws Exception;
}
