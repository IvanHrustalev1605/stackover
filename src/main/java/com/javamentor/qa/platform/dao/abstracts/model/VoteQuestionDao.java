package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;

public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {

    VoteQuestion getByUserIdQuestionId(Long userId,Long questionId);

    Long userVotedUp(Long userId);
    Long getSumUpVote(Long questionId);





}
