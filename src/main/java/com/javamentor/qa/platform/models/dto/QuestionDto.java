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
@Schema(description = "Dto для HTTP ответа")
public class QuestionDto {
    @Schema(description = "id вопроса")
    private Long id;
    @Schema(description = "оглавление вопроса")
    private String title;
    @Schema(description = "id автора")
    private Long authorId;
    @Schema(description = "имя атора")
    private String authorName;
    @Schema(description = "картинка автора")
    private String authorImage;
    @Schema(description = "описание вопроса")
    private String description;
    @Schema(description = "репутация автора")
    private Long authorReputation;
    @Schema(description = "количество ответов")
    private int countAnswer;
    @Schema(description = "счетчик ценности")
    private int countValuable;
    @Schema(description = "дата и время вопроса")
    private LocalDateTime persistDateTime;
    @Schema(description = "последнее обновление вопроса")
    private LocalDateTime lastUpdateDateTime;
    @Schema(description = "теги в вопросе")
    private List<TagDto> listTagDto;
}
