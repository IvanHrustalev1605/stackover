package com.javamentor.qa.platform.models.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto {
    private String title;
    private String description;
    private List<TagDto> tags;

}
