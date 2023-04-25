package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Логин и пароль")
public class AuthenticationRequestDTO {
    @NotBlank
    @Schema(description = "Логин")
    private String login;
    @NotBlank
    @Schema(description = "Пароль")
    private String pass;

}
