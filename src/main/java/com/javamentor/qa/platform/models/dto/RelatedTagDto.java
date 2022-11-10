package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Связанный тег")
public class RelatedTagDto {

    @Parameter(description = "id тега")
    private Long id;

    @Parameter(description = "Заголовок вопроса")
    private String title;

    @Parameter(description = "количесвто вопросов")
    private Long countQuestion;


}
