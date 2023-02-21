package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO игнорируемого пользователем тега")
public class IgnoredTagDto {
    @Parameter(description = "ID игнорируемого тега")
    private Long id;
    @Schema(description = "Название игнорируемого тега")
    private String name;
}
