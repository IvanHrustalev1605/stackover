package com.javamentor.qa.platform.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TokenResponseDto {

    @NotEmpty
    private String role;
    @NotEmpty
    private String token;

}
