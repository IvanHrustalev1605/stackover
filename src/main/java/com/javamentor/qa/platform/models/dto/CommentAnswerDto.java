package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comment to Answer")
public class CommentAnswerDto {
    @Parameter(description = "Comment id")
    private Long id;
    @Schema(description = "Answer id")
    private Long answerId;
    @Schema(description = "Date & Time comment was updated")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "Date & Time comment was created")
    private LocalDateTime persistDate;
    @Schema(description = "Comment text")
    private String text;
    @Schema(description = "User id")
    private Long userId;
    @Schema(description = "User avatar image url")
    private String imageLink;
    @Schema(description = "User reputation")
    private Long reputation;
}
