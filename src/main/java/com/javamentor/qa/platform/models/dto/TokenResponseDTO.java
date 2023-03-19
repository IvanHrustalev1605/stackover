package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenResponseDTO {
    @NotEmpty
    @Schema(description = "роль пользователя")
    private String role;
    @NotEmpty
    @Schema(description = "jwt токен")
    private String token;
}