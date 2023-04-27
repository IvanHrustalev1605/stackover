package com.javamentor.qa.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class QuestionCommentDto {
    @ApiModelProperty(notes = "id комментария")
    private Long id;
    @ApiModelProperty(notes = "id вопроса")
    private Long questionId;
    @ApiModelProperty(notes = "дата редактирования комментария")
    private LocalDateTime lastRedactionDate;
    @ApiModelProperty(notes = "дата создания комментария")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @ApiModelProperty(notes = "текст комментария")
    private String text;
    @NotNull
    @ApiModelProperty(notes = "id пользователя")
    private Long userId;
    @ApiModelProperty(notes = "ссылка на изображение пользователя")
    private String imageLink;
    @ApiModelProperty(notes = "репутация пользователя")
    private Long reputation;

}
