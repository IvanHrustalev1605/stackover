package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

import java.util.Optional;


public interface ReputationDao extends ReadWriteDao<Reputation, Long> {
    Optional<Reputation> getReputationByUserId(Long id, ReputationType type);
    Optional<Reputation> getByAnswerIdAndUserId(Long answerId, Long userId);
    void UpReputationForQuestion (User user, Long questionId);
    Optional<Reputation> getByQuestionId(Long questionId);
}
