package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> getReputationByQuestionAndAuthor(Long questionId, Long authorId, ReputationType type) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT
                 r
                  FROM Reputation r
                   WHERE r.question.id =:questionId AND r.author.id =:authorId AND r.type =:type
                """, Reputation.class)
                .setParameter("questionId", questionId)
                .setParameter("authorId", authorId)
                .setParameter("type", type));
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

}
