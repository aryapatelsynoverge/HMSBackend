package com.system.service;

public interface EmailService {
    void sendSetPasswordLink(String toEmail) throws Exception;

    void sendOtp(String toEmail, String otp) throws Exception;

}
