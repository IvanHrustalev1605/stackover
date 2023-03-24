package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;

import java.util.Optional;

public interface CommentAnswerDtoService {

    Optional<CommentAnswerDto> getCommentAnswerDtoById(Long answerId);
}
