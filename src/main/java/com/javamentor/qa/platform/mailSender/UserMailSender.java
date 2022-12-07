package com.javamentor.qa.platform.mailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserMailSender {

    @Value("${spring.mail.username}")
    private String fromAddress;

    private JavaMailSender javaMailSender;

    @Autowired
    public UserMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(String emailTo, String subject,String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(fromAddress);       // Отправитель
        mailMessage.setTo(emailTo);            //Получатель
        mailMessage.setSubject(subject);      // тема
        mailMessage.setText(message);        // контент

        javaMailSender.send(mailMessage);
    }


}
