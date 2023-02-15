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
@Schema(description = "DTO отслеживаемого пользователем тега")
public class TrackedTagDto {

    @Parameter(description = "id отслеживаемого тега")
    private Long id;
    @Schema(description = "название отслеживаемого тега")
    private String name;
}
