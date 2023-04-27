package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import java.util.List;

public interface CommentDtoDao extends ReadWriteDao<QuestionCommentDto, Long> {
    List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId);
}
