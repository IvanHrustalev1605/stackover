package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "пользователь для регистрации")
public class UserRegistrationDto {
    private Long id;
    @NotEmpty
    @Schema(description = "имя пользователя")
    private String firstName;
    @NotEmpty
    @Schema(description = "email пользователя")
    private String email;
    @NotEmpty
    @Schema(description = "пароль пользователя")
    private String password;

}
