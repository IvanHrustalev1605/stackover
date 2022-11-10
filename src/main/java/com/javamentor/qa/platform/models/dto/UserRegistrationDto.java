package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "регистрация пользователя")
public class UserRegistrationDto {

    @NotEmpty
    @Parameter(description = "имя пользователя")
    private String firstName;
    @NotEmpty
    @Parameter(description = "фамилия пользователя")
    private String lastName;
    @NotEmpty
    @Parameter(description = "почта пользователя")
    private String email;
    @NotEmpty
    @Parameter(description = "пароль пользователя")
    private String password;
    @Parameter(description = "код активации")
    private String activationCode;

}
