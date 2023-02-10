package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.MailService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
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

    private final UserService userService;
    private final MailService mailService;

    public RegistrationController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping
    @ApiOperation("Регистрация пользователя")
    @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно")
    @ApiResponse(responseCode = "404", description = "Ошибка при регистрации")
    ResponseEntity<String> sendMessage(@RequestBody UserRegistrationDto userRegistrationDto) {

        if (!registerNewUserRegistrationDto(userRegistrationDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify/{verifCode}")
    @ApiOperation("Подтверждение регистрации пользователя")
    @ApiResponse(responseCode = "200", description = "Аккаунт подтвержден")
    @ApiResponse(responseCode = "404", description = "Ошибка при подтверждении регистрации")
    ResponseEntity<User> verify(@PathVariable String verifCode) {

        if (!verifyUserRegistrationDto(verifCode)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public boolean registerNewUserRegistrationDto(UserRegistrationDto userRegistrationDto) {

        User user = UserConverter.INSTANCE.userRegistrationDtoToUser(userRegistrationDto);

        if (userService.getByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        user.setIsEnabled(false);
        user.setVerifCode(UUID.randomUUID().toString());
        userService.persist(user);

        if (StringUtils.hasLength(user.getEmail())) {
            String message = String.format("Hello, %s! \n +" +
                    "To finish registration process please visit this link: \n" +
                    "http://localhost:8080/api/user/registration/verify/%s", user.getFullName(), user.getVerifCode());
            mailService.sendSimpleEmail(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public boolean verifyUserRegistrationDto(String verifCode) {

        Optional<User> user = userService.getByVerifCode(verifCode);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setVerifCode(null);
        user.get().setIsEnabled(true);
        userService.update(user.get());
        return true;
    }
}
