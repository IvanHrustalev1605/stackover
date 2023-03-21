package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {
    private QuestionDaoImpl questionDao;
    @PersistenceContext
    private EntityManager entityManager;
    public ReputationDaoImpl(EntityManager entityManager, QuestionDaoImpl questionDao) {
        this.entityManager = entityManager;
        this.questionDao = questionDao;
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
    public void UpReputationForQuestion(User user, Long questionId) {
        if (getByQuestionId(questionId).isEmpty()) {
            Reputation reputation = new Reputation();
            reputation.setCount(10);
            Question question = questionDao.findQuestionById(questionId).get();
            reputation.setQuestion(question);
            reputation.setType(ReputationType.Question);
            reputation.setAuthor(question.getUser());
            reputation.setSender(user);
            entityManager.persist(reputation);
        }
    }
    @Override
    public Optional<Reputation> getByQuestionId(Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select r from Reputation r where r.question.id = :questionId",Reputation.class)
                .setParameter("questionId", questionId));
    }
}

