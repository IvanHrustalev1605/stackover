package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentDtoDao {
    List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId);
}
