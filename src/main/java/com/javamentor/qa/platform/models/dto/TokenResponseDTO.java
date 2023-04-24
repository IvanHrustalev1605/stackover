package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Jwt токен")
public class TokenResponseDTO {
    @NotEmpty
    @Schema(description = "Роль Юзера")
    private String role;
    @NotEmpty
    @Schema(description = "Токен")
    private String token;

}
