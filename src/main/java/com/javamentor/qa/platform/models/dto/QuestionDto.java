package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.Question;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Вопрос dto")

public class QuestionDto {
    @Parameter(description = "ID вопроса")
    private Long id;
    @Schema(description = "Заголовок")
    private String title;
    @Schema(description = "ID автора")
    private Long authorId;
    @Schema(description = "Имя Автора")
    private String authorName;
    @Schema(description = "Аватар автора")
    private String authorImage;
    @Schema(description = "содержимое вопроса")
    private String description;
    @Schema(description = "Репутация автора")
    private Long authorReputation;
    @Schema(description = "счетчик ответов")
    private int countAnswer;
    @Schema(description = "счетчик рейтинга")
    private int countValuable;
    @Schema(description = "Дата создания вопроса")
    private LocalDateTime persistDateTime;
    @Schema(description = "дата последнего обновления вопроса")
    private LocalDateTime lastUpdateDateTime;
    @Schema(description = "Список тегов")
    private List<TagDto> listTagDto;

}
