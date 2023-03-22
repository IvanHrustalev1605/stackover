package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {
    private QuestionService questionService;
    @PersistenceContext
    private EntityManager entityManager;
    public ReputationDaoImpl(EntityManager entityManager, QuestionService questionService) {
        this.entityManager = entityManager;
        this.questionService = questionService;
    }
    @Override
    public Optional<Reputation> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                                        select r from VoteAnswer r where r.answer.id = :answerId
                                        and r.user.id = :userId
                                        """)
                        .setParameter("answerId", answerId)
                        .setParameter("userId", userId));
    }

    @Override
    public Optional<Reputation> getReputationByUserId(Long id, ReputationType type) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                                          select r from Reputation as r
                                          where r.author.id = :authorId and r.type = :type
                                          """, Reputation.class)
                .setParameter("authorId", id)
                .setParameter("type", type));
    }

    @Override
    public Optional<Reputation> getByQuestionId(Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select r from Reputation r where r.question.id = :questionId",Reputation.class)
                .setParameter("questionId", questionId));
    }
}

