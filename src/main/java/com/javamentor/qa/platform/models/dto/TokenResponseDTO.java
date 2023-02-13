package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO с ролью и JWT токеном пользователя")
public class TokenResponseDTO {
    @NotEmpty
    @Schema(description = "Роль")
    private String role;
    @NotEmpty
    @Schema(description = "JWT токен")
    private String token;
}
