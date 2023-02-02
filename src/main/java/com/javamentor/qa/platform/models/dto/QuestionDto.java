package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuestionDto {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private String authorImage;
    private String description;
    private Long authorReputation;
    private int countAnswer;
    private int countValuable;
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<TagDto> listTagDto;

}
