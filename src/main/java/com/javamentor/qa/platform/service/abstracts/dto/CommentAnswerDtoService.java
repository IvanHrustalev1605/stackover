package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;

public interface CommentAnswerDtoService {
    CommentAnswerDto addCommentToAnswerDto(CommentAnswer commentAnswer);
}
