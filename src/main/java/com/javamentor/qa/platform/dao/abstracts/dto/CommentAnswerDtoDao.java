package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;

import java.util.Optional;

public interface CommentAnswerDtoDao {
    Optional<CommentAnswerDto> getCommentAnswerDtoById(Long answerId);
}
