package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO с данными для аутентификации")
public class AuthenticationRequestDTO {
    @NotBlank
    @Schema(description = "Логин для аутентификации")
    private String login;
    @NotBlank
    @Schema(description = "Пароль для аутентификации")
    private String pass;
}
