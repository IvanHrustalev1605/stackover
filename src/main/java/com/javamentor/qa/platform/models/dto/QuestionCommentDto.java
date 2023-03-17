package com.javamentor.qa.platform.models.dto;


import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@AllArgsConstructor
public class QuestionCommentDto {
    private Long id;
    private Long questionId;
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
