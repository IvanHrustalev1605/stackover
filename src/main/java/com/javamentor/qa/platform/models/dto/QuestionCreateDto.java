package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Вопрос создать")
public class QuestionCreateDto {
    private String title;
    private String description;
    private List<TagDto> tags;
}
