package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {
    private final ReputationDao reputationDao;
    private final QuestionService questionService;
    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao, ReputationDao reputationDao, QuestionService questionService) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
        this.questionService = questionService;
    }

    @Override
    public Optional<Reputation> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return reputationDao.getByAnswerIdAndUserId(answerId, userId);
    }

    @Override
    public Optional<Reputation> getReputationByUserId(Long id, ReputationType type) {
        return reputationDao.getReputationByUserId(id, type);
    }
    @Transactional
    @Override
    public void UpReputationForQuestion(Long questionId, User user) {
            if (reputationDao.getByQuestionId(questionId).isEmpty()) {
                Reputation reputation = new Reputation();
                reputation.setCount(10);
                Question question = questionService.findQuestionById(questionId).get();
                reputation.setQuestion(question);
                reputation.setType(ReputationType.Question);
                reputation.setAuthor(question.getUser());
                reputation.setSender(user);
                reputationDao.persist(reputation);
            }
        }
}

