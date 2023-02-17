package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentDtoDao {
    Optional<List<QuestionCommentDto>> getAllQuestionCommentDtoById(Long userId);
}
