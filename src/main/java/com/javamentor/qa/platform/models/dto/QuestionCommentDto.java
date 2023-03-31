package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class QuestionCommentDto {
    private Long comment_id;
    private Long question_id;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    private String text;
    @NotNull
    private Long userId;
    private String imageLink;
    private Long reputation;
}
