package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDto {
    @Schema(description = "id вопроса")
    private Long id;
    @Schema(description = "заголовок вопроса")
    private String title;
    @Schema(description = "id автора вопроса")
    private Long authorId;
    @Schema(description = "имя автора вопроса")
    private String authorName;
    @Schema(description = "ссылка на изображение автора вопроса")
    private String authorImage;
    @Schema(description = "описание вопроса")
    private String description;
    @Schema(description = "репутация автора вопроса")
    private Long authorReputation;
    @Schema(description = "количество ответов")
    private int countAnswer;
    @Schema(description = "количество голосов за вопроса")
    private int countValuable;
    @Schema(description = "время создания вопроса")
    private LocalDateTime persistDateTime;
    @Schema(description = "время последнего редактирования вопроса")
    private LocalDateTime lastUpdateDateTime;
    @Schema(description = "список тэгов вопроса")
    private List<TagDto> listTagDto;

}