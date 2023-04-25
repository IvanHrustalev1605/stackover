package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.UserAlreadyExistException;
import com.javamentor.qa.platform.exception.VerificationTokenExpiredException;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.registration.OnRegistrationCompleteEvent;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.converters.UserRegistrationMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user/registration")
@Api(description = "отправка ссылки на подтверждение регистрации")
public class RegistrationController {

    @Value("${EXPIRATION_TIME_IN_MINUTES}")
    private int EXPIRATION_TIME_IN_MINUTES;
    private final UserRegistrationMapper userMapper;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRegistrationMapper userMapper, UserService userService, ApplicationEventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
    }
    @ApiOperation("Отправка пользователю email с токеном для подтверждения документации")
    @PostMapping
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
    @GetMapping("/verify")
    @ApiOperation("Подтверждение регистрации")
    public ResponseEntity<String> confirmRegistration(@RequestParam String token) {
        User user = userService.getByToken(token);
        if (user == null) {
            throw new UsernameNotFoundException("Ошибка в данных на подтверждение регистрации. Попробуйте еще раз");
        }
        if (LocalDateTime.now().getNano() - userService.getTokenByToken(token).getExpiryDate().getNano() > EXPIRATION_TIME_IN_MINUTES * 6000000) {
            user.setIsEnabled(false);
            userService.persist(user);
            throw new VerificationTokenExpiredException("Извините, время подтверждения Вашего аккаунта истекло");
        }
        return new ResponseEntity<>(  "Спасибо! Ваша учетная запись успешно подтверждена!"+user, HttpStatus.OK);
    }
}
