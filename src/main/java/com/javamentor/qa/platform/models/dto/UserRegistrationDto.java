package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Регистрация пользователя dto")
public class UserRegistrationDto {

    @NotEmpty(message = "Введите имя!")
    @Schema(description = "Имя пользователя")
    private String firstName;

    @NotEmpty(message = "Введите фамилию!")
    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @NotEmpty(message = "Введите адрес почты!")
    @Schema(description = "Электронная почта польвателя")
    private String email;

    @NotEmpty(message = "Введите пароль!")
    @Schema(description = "Пароль пользователя")
    private String password;


}
