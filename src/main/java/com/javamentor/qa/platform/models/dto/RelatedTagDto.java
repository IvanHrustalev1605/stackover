package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Связанные метки")
public class RelatedTagDto {
    @Parameter(description = "id метки")
    Long id;
    @Parameter(description = "Заглавие вопроса")
    String title;
    @Parameter(description = "Кол-во вопросов")
    Long countQuestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(Long countQuestion) {
        this.countQuestion = countQuestion;
    }
}
