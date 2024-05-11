package com.system.service.impl;

import com.system.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
@Data
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendSetPasswordLink(String toEmail) throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
                <div>
                    <span> Hi , please set your password : </span>
                    <a href="http://localhost:4200/verifyEmail">Click here to set password </a>
                </div>\s
                """, true);
        javaMailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void sendOtp(String toEmail, String otp) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("OTP for verification");
        mimeMessageHelper.setText("""
                <div>
                <p>OTP for verification : %s </p>
                </div>
                """.formatted(otp), true);
        javaMailSender.send(mimeMessage);
    }
}
