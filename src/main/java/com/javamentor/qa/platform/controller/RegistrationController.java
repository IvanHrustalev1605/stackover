package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/user/registration")
@PropertySource("application.properties")
public class RegistrationController {
    @Value("${EXPIRATION_TIME_IN_MINUTES}")
    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("${ADDRESS_FROM_SEND}")
    private String fromAddress;
    @Value("${}")
    private String senderName;
    @Value("${spring.mail.host}")
    private String host;

    private ApplicationEventPublisher applicationEventPublisher;

    public RegistrationController(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @PostMapping
    public ResponseEntity registerUser(@RequestBody UserRegistrationDto user) {
        return new ResponseEntity(HttpStatus.OK);
    }

}
