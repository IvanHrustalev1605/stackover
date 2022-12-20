package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {

    public void getQuestionUpVote(User user, Long questionId);
    Long getSumUpVote(Long questionId);

}
