package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;

public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {

    Long getCountOfQuestion(Long questionId);
    VoteQuestion getVoteQuestionByQuestionAndUserIds(Long questionId, Long userId);
}
