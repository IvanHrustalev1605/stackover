package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

import java.util.Optional;

public interface ReputationDao extends ReadWriteDao<Reputation, Long> {
    Optional<Reputation> getReputationByQuestionAndAuthor(Long questionId, Long authorId, ReputationType type);
    Optional<Reputation> getByAnswerIdAndUserId(Long answerId, Long userId);
}
