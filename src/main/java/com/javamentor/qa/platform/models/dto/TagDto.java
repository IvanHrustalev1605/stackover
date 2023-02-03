package com.javamentor.qa.platform.models.dto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тэг dto")
public class TagDto {
    @Parameter(description = "ID Тэга")
    private Long id;
    @Schema(description = "Название тэгп")
    private String name;
    @Schema(description = "Описание тэга")
    private String description;

}
