package com.javamentor.qa.platform.webapp.configs.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.configs.converters.UserConverter;
import com.javamentor.qa.platform.mailSender.UserMailSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user/registration")
@Tag(name = "Регистрация", description = "Регистрация пользователя")
public class RegistrationController {

//    @Value("")
//    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("{spring.mail.username}")
    private String fromAddress;
    @Value("{mail.sendername}")
    private String senderName;
    @Value("{spring.mail.host}")
    private String host;

    private final UserService userService;

    private final UserMailSender userMailSender;

    @Autowired
    public RegistrationController(UserService userService, UserMailSender userMailSender) {
        this.userService = userService;
        this.userMailSender = userMailSender;
    }


    @PostMapping()
    @Operation(summary = "Отправка письма с кодом для активации ", description = "Подтвердите адрес электронной почты ", responses = {
            @ApiResponse(responseCode = "200", description = "Письмо отправлено"),
            @ApiResponse(responseCode = "400", description = "Письмо не отправлено")})
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {

        User user = UserConverter.INSTANCE.userRegistrationDtoToUser(userRegistrationDto);

        Optional<User> userFromDb = userService.getByEmail(user.getEmail());

        if (userFromDb.isEmpty()) {

            user.setIsEnabled(false);
            userService.persist(user);

            String message = "Hello, " + user.getFullName() + " ! " +
                    "Welcome to Stackover. Please, activate code: " + user.getPersistDateTime();

            userMailSender.sendMessage(user.getEmail(), "Activate code", message);


            return new ResponseEntity<>("User registered successfully, please, activate account", HttpStatus.OK);
        }

        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify/{code}/{email}")
    public ResponseEntity<User> activate(@PathVariable String code, @PathVariable String email) {

        Optional<User> user = userService.getByEmail(email);
        String activate = String.valueOf(user.get().getPersistDateTime());
        int compare = code.compareTo(activate);

        if (user.isPresent() && compare == 0) {
            user.get().setIsEnabled(true);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

