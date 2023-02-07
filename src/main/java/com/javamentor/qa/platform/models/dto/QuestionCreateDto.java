package com.javamentor.qa.platform.models.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Создание вопроса dto")
public class QuestionCreateDto {
    @Schema(description = "Заголовок вопроса")
    private String title;
    @Schema(description = "Содержание вопроса")
    private String description;
    @Schema(description = "Список тэгов")
    private List<TagDto> tags;

}
