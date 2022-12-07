package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
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
@Schema(description = "Регистрация_пользователя")
public class UserRegistrationDto {

    @NotEmpty
    @Parameter(description = "имя пользователя")
    private String firstName;

    @NotEmpty
    @Parameter(description = "фамилия пользователя")
    private String lastName;

    @NotEmpty
    @Parameter(description = "email пользователя")
    private String email;

    @NotEmpty
    @Parameter(description = "пароль пользователя")
    private String password;

}
