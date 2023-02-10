package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<Reputation> getReputationByAnswerAndUser(Long answerId, Long userId) {
        return reputationDao.getReputationByAnswerAndUser(answerId, userId);
    }
    @Override
    public Optional<Reputation> getReputationByVoteQuestion(Long userId, Long questionId) {
        return reputationDao.getReputationByVoteQuestion(userId, questionId);
    }

}
