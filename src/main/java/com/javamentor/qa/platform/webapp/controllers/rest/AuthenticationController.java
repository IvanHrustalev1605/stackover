package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.service.abstracts.dto.TokenResponseDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth контроллер", description = "Аутентификация и авторизация user'a и получение токена jwt")
@Api(value = "Контроллер аутентификации")
public class AuthenticationController {
    private final TokenResponseDtoService tokenResponseDtoService;

    public AuthenticationController(TokenResponseDtoService tokenResponseDtoService) {
        this.tokenResponseDtoService = tokenResponseDtoService;
    }

    @ApiOperation("Получение юзера по id")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Юзер успешно аутентифицирован и авторизован"),
            @ApiResponse(code = 403, message = "Не правильно введен логин или пароль")})
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenResponseDtoService.getTokenResponseDto(authenticationRequestDTO));
    }

    @ApiOperation("logout")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.ok().build(); //шаблон
    }
}
