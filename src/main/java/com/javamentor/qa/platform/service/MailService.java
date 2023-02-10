package com.javamentor.qa.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
}
