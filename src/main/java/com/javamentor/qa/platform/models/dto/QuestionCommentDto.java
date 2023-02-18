package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Dto комментарий к вопросу")
public class QuestionCommentDto {
    @Schema(description = "ID комментария")
    private Long id;
    @Schema(description = "ID вопроса")
    private Long questionId;
    @Schema(description = "Время последнего редактирования комментария")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "Время сохранения комментария")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Schema(description = "Текст комментария")
    private  String text;
    @NotNull
    @Schema(description = "ID юзера, оставившего комментарий")
    private Long userId;
    @Schema(description = "Ссылка на изображение пользователя")
    private String imageLink;
    @Schema(description = "репутация комментария")
    private Integer reputation;
}
