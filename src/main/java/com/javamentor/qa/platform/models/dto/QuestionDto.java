package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "вопрос")
public class QuestionDto {
    @Parameter(description = "id вопроса")
    private Long id;
    @Schema(description = "Тема вопроса")
    private String title;
    @Parameter(description = "id автора")
    private Long authorId;
    @Schema(description = "Имя автора")
    private String authorName;
    @Schema(description = "Изображение")
    private String authorImage;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Репутация")
    private Long authorReputation;
    @Schema(description = "Кол-во ответов")
    private int countAnswer;
    @Schema(description = "")
    private int countValuable;
    @Schema(description = "")
    private LocalDateTime persistDateTime;
    @Schema(description = "")
    private LocalDateTime lastUpdateDateTime;
    @Schema(description = "")
    private List<TagDto> listTagDto;
}
