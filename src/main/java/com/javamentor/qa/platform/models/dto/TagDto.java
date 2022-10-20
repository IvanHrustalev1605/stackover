package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "тег")
public class TagDto {

    @Parameter(description = "id пользователя")
    private Long id;
    @Schema(description = "почта пользователя")
    private String name;
    @Schema(description = "почта пользователя")
    private String description;
}
