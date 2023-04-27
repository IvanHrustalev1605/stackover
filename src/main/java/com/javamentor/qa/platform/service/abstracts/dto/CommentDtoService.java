package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;

public interface CommentDtoService extends ReadWriteService<QuestionCommentDto, Long> {
    List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId);
}
