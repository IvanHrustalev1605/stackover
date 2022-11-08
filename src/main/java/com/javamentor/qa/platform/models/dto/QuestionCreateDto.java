package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Dto для HTTP запроса")
public class QuestionCreateDto {
    @NonNull
    @Schema(description = "оглавление вопроса")
    private String title;
    @NonNull
    @Schema(description = "описание вопроса")
    private String description;
    @NonNull
    @Schema(description = "теги в вопросе")
    private List<TagDto> tags;
}
