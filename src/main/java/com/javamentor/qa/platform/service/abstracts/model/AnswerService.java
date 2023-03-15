package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    void setDeleteById(Long answerId, Long questionId);
    Optional<Answer> getAnswerForVote(Long answerId, Long userId);
}
