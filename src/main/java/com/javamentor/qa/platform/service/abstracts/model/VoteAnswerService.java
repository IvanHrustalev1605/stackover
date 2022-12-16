package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    Long countVotes(Long answerId);
    Long voteDownForAnswer(Answer answer, User user, Long repCount, VoteType voteType);


}
