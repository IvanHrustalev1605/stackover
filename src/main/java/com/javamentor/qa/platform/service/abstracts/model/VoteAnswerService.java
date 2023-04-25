package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    Long upVote(Answer answer, User user);
    Long vote(Answer answer, User sender, Integer repCount, VoteType voteType);
}
