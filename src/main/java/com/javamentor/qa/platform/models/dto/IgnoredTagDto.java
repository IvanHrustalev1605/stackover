package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Karina Bashkatova.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "игнорируемый тег")
public class IgnoredTagDto {

    @Parameter(description = "id игнорируемого тега")
    private Long id;
    @Schema(description = "имя игнорируемого тега")
    private String name;
}
