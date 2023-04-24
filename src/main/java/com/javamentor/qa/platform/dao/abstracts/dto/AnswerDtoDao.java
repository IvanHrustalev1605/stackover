package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnswerDtoDao {
    List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId);
}
