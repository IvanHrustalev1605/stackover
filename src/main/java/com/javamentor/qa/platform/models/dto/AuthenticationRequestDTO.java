package com.javamentor.qa.platform.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class AuthenticationRequestDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String pass;

}
