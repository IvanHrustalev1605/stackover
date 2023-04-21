package com.javamentor.qa.platform.models.entity.registration;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VerifTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private UserService userService;
    private MessageSource messageSource;
    private VerifTokenService tokenService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    public RegistrationListener(UserService userService, MessageSource messageSource, VerifTokenService tokenService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.tokenService = tokenService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("khrustalev16@gmail.com");
        mailSender.setPassword("7x87rZ8E.");
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl =
                event.getAppUrl() + "/registrationConfirm?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setFrom("khrustalev16@bk.ru");
        mailMessage.setTo(recipientAddress);
        mailMessage.setText("http://localhost:8080" + confirmationUrl);
        mailSender.send(mailMessage);
    }
}
