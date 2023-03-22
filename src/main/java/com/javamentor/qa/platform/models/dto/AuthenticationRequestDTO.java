
package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDTO {
    @NotBlank
    @Schema(description = "логин")
    private String login;
    @NotBlank
    @Schema(description = "пароль")
    private String pass;
}