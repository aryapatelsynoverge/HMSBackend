package com.system.service.impl;


import com.system.exceptionhandling.InvalidOTPException;
import com.system.exceptionhandling.UserNotFoundException;
import com.system.model.User;
import com.system.repository.UserRepository;
import com.system.service.ResetPasswordService;
import com.system.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;
import com.system.audit.EntityAuditorAware;

import java.util.Optional;
import java.util.Random;

@Service
@Data
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BasicTextEncryptor textEncryptor;

    /**
     * Verify email
     *
     * @param userEmail
     * @throws Exception
     */
    @Override
    public void verifyEmail(String userEmail) throws Exception {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("No user exist with this email");
        }
        String otp = generateOtp();
        EntityAuditorAware.sessionEmail = userEmail;
        existingUser.ifPresent(user -> {
            user.setOtp(otp);
            userRepository.save(user);
        });
        emailService.sendOtp(userEmail, otp);
    }

    /**
     * Verify OTP
     *
     * @param userEmail
     * @param otp
     * @throws Exception
     */
    @Override
    public void verifyOtp(String userEmail, String otp) throws Exception {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("Verify user email ID first");
        }
        if(!existingUser.get().getOtp().equalsIgnoreCase(otp)){
            throw new InvalidOTPException("Invalid OTP");
        }
    }

    /**
     * Set new password
     *
     * @param userEmail
     * @param password
     */
    @Override
    public void setPassword(String userEmail, String password)throws Exception{
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("Verify user email ID first");
        }
        existingUser.ifPresent(user -> {
            String encryptedPassword = textEncryptor.encrypt(password);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        });
    }

    /**
     * Resend OTP
     *
     * @param userEmail
     * @throws Exception
     */
    @Override
    public void resendOtp(String userEmail) throws Exception {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("Email ID does not exist");
        }
        String otp = generateOtp();
        existingUser.ifPresent(user -> {
            user.setOtp(otp);
            userRepository.save(user);
        });
        emailService.sendOtp(userEmail, otp);
    }

    /**
     * Generate new OTP
     *
     * @return
     */
    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        return String.format("%06d", randomNumber);
    }
}
