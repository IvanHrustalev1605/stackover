package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    @Override
    Optional<VoteAnswer> getByAnswerIdAndUserId(Long answerId, Long userId);


}
