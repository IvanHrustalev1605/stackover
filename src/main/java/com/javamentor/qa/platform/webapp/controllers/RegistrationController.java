package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/user/registration")
public class RegistrationController {

    @Value("${spring.mail.EXPIRATION_TIME_IN_MINUTES}")
    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("${spring.mail.username}")
    private String senderName;
    @Value("${spring.mail.host}")
    private String host;

    private final UserRegistrationDtoService userRegistrationDtoService;

    public RegistrationController(UserRegistrationDtoService userRegistrationDtoService) {
        this.userRegistrationDtoService = userRegistrationDtoService;
    }

    @PostMapping
    private ResponseEntity<UserRegistrationDto> sendMessage(@RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDtoService.addRegisterUserDto(userRegistrationDto)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify")
    private ResponseEntity<UserRegistrationDto> verify(@RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDtoService.verifyUserRegistrationDto(userRegistrationDto)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
