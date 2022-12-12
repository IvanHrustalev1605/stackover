package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    List<AnswerDto> getAllAnswerDtoQuestionId(Long userId, Long questionId);

    void markAnswerAsDelete(Long answerId);
}
