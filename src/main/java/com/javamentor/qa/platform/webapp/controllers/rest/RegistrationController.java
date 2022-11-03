package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final UserRegistrationDtoService userRegistrationDtoService;

    public RegistrationController(UserRegistrationDtoService userRegistrationDtoService) {
        this.userRegistrationDtoService = userRegistrationDtoService;
    }

    @PostMapping
    @ApiOperation("Регистрация user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно"),
            @ApiResponse(responseCode = "414", description = "Ошибка в условии регистрации")
    })
    private ResponseEntity<UserRegistrationDto> sendMessage(@RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDtoService.addUserRegistrationDto(userRegistrationDto)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify/{activationCode}")
    @ApiOperation("Отправляем сообщение user, содержащее ссылку с подтверждением email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email подтвержден"),
            @ApiResponse(responseCode = "414", description = "Ошибка в условии подтверждения")
    })
    private ResponseEntity<UserRegistrationDto> verify(@PathVariable String activationCode) {
        if(!userRegistrationDtoService.verifyUserRegistrationDto(activationCode)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
