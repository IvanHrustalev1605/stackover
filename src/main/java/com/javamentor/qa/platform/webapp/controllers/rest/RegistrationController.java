package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("api/user/registration")
public class RegistrationController {

    @Value("${ver_token_expiration_time}")
    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("${email_from_address}")
    private String fromAddress;
    @Value("${email_sender_name}")
    private String senderName;
    @Value("${email_host}")
    private String host;

    private final UserConverter userConverter;
    private final UserService userService;
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final String SENDER_PASS = "Lw3Y2grM9cy4s2bBpwMq";
    private final String SERVER_ADDRESS = "http://localhost:8080/api/user/registration/verify/";
    private final int SMTP_PORT = 465;

    public RegistrationController(UserConverter userConverter, UserService userService, RoleService roleService, EntityManager entityManager) {
        this.userConverter = userConverter;
        this.userService = userService;
        this.roleService = roleService;
        this.entityManager = entityManager;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<String> receiveUserRegistrationInfo(@RequestBody UserRegistrationDto urDto) {

        if (userService.getByEmail(urDto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).build();
        }
        User user = userConverter.userRegistrationDtoToUser(urDto);
        String confirmationToken = UUID.randomUUID().toString();
        user.setAbout(confirmationToken);
        user.setRole(roleService.getByName("ROLE_USER").get());
        user.setIsEnabled(false);

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddress, SENDER_PASS);
            }
        });
//        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress, senderName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(urDto.getEmail()));
            message.setSubject("Email confirmation");
            message.setContent(String.format("""
                    <p>Hi %s %s!To confirm your email, click on the link below</p>
                    <a href="%s%s">Confirm</a>""",
                    urDto.getFirstName(), urDto.getLastName(), SERVER_ADDRESS, confirmationToken),"text/html");
            Transport.send(message);
            userService.persist(user);
            return ResponseEntity.ok().build();

        } catch (MessagingException | UnsupportedEncodingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> emailVerification(@PathVariable("token") String token) {
        Optional<User> queryUser = SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("SELECT u FROM User u WHERE u.about = :token", User.class)
                .setParameter("token", token));
        if (queryUser.isPresent()) {
            if (ChronoUnit.MINUTES.between(
                    queryUser.get().getPersistDateTime(), LocalDateTime.now()) > EXPIRATION_TIME_IN_MINUTES) {
                ResponseEntity.status(498).build();
            }
            queryUser.get().setAbout("");
            queryUser.get().setIsEnabled(true);
            userService.update(queryUser.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
