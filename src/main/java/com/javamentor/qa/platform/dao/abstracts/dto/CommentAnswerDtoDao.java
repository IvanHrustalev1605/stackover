package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;


public interface CommentAnswerDtoDao {
    CommentAnswerDto getCommentAnswerDto(Long commentAnswerId);
}
