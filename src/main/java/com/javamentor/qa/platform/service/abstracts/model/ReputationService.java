package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import java.util.Optional;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    Optional<Reputation> getByAnswerIdAndUserId(Long answerId, Long userId);
}
