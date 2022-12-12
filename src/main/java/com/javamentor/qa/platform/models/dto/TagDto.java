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
@Schema(description = "Метка")
public class TagDto {
    @Parameter(description = "id метки")
    private Long id;
    @Schema(description = "имя метки")
    private String name;
    @Schema(description = "описание метки")
    private String description;
}
