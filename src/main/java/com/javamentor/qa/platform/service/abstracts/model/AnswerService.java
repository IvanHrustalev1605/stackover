package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface AnswerService extends ReadWriteService<Answer, Long> {

    @Override
    Optional<Answer> getById(Long id);

    @Override
    Optional<Answer> getByAnswerIdAndUserId(Long answerId, Long userId);


    Long increaseVoteAnswer(Long answerId, Long userId);
}
