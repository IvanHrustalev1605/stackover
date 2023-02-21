package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedTagDto {
    private Long id;
    private String title;
    private Long countQuestion;
}
