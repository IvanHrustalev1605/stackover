package com.javamentor.qa.platform.models.entity.registration;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VerifTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.UUID;

@Configuration
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private UserService userService;
    private MessageSource messageSource;
    private VerifTokenService tokenService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    public RegistrationListener(UserService userService, MessageSource messageSource, VerifTokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Подтверждение регистрации";
        String confirmationUrl =
                event.getAppUrl() + "/api/user/registration/registrationConfirm?token=" + token;

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom("khrustalev16@bk.ru");
            mimeMessage.setRecipients(Message.RecipientType.TO, recipientAddress);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent("" +
                    "<p>Ваше имя пользователя: " + user.getEmail() + "</p>" +
                    "<a href=http://localhost:8080" + confirmationUrl + ">" +
                    "Для подтверждения регистрации нажмите на меня"  + "</a>", "text/html; charset = UTF-8" );

        };
        mailSender.send(preparator);

    }
}
