package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.registration.OnRegistrationCompleteEvent;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserRegistrationMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/registration")
public class RegistrationController {
    private UserRegistrationMapper userMapper;
    private UserService userService;
    private ApplicationEventPublisher eventPublisher;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRegistrationMapper userMapper, UserService userService, ApplicationEventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/verify")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto user,
                                             HttpServletRequest request) {
        User userRegistered = userMapper.toEntity(user);
        userRegistered.setPassword(passwordEncoder.encode(userRegistered.getPassword()));
        userService.persist(userRegistered);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                appUrl, request.getLocale(), userRegistered
        ));
        return new ResponseEntity<User>(userRegistered, HttpStatus.OK);
    }
}
