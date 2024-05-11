package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Random;


@SpringBootApplication
@EnableAsync
public class HospitalManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementSystemApplication.class, args);
    }

    @Bean
    public BasicTextEncryptor textEncryptor() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("MyS3cr3tK3y".toCharArray());
        return textEncryptor;
    }
}
