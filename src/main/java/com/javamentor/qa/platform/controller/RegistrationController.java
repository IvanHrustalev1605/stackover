package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.exception.UserAlreadyExistException;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.registration.OnRegistrationCompleteEvent;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserRegistrationMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/registration")
@Api(description = "отправка ссылки на подтверждение регистрации")
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
    @ApiOperation("Отправка пользователю email с токеном для подтверждения документации")
    @PostMapping("/verify")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto user,
                                             HttpServletRequest request) {
        User userRegistered = userMapper.toEntity(user);
        if (userService.getByEmail(userRegistered.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь " + userRegistered.getEmail()+ " уже зарегистрирован!");
        } else {
            userRegistered.setPassword(passwordEncoder.encode(userRegistered.getPassword()));
            userService.persist(userRegistered);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                    appUrl, request.getLocale(), userRegistered
            ));
            return new ResponseEntity<User>(userRegistered, HttpStatus.OK);
        }
    }
    @GetMapping("/registrationConfirm")
    @ApiOperation("Подтверждение регистрации")
    public ResponseEntity confirmRegistration(@RequestParam String token) {
        User user = userService.getByToken(token);
        if (user == null) {
            throw new UsernameNotFoundException("Ошибка в данных на подтверждение регистрации. Попробуйте еще раз");
        }
        user.setEnabled(true);
        userService.persist(user);
        return new ResponseEntity<>("Спасибо! Ваша учетная запись успешно подтверждена!", HttpStatus.OK);
    }
}
