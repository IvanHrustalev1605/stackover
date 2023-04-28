package com.javamentor.qa.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelatedTagDto {
    @ApiModelProperty(notes = "id тега")
    private Long id;
    @ApiModelProperty(notes = "название тега")
    private String title;
    @ApiModelProperty(notes = "количество вопросов")
    private Long countQuestion;
}
