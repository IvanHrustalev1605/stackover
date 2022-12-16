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
    public Optional<Reputation> getReputationBySenderAndQuestion(Long senderId, Long questionId, ReputationType type) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT r
                FROM Reputation r
                WHERE r.sender.id = :sender
                AND r.question.id = :question
                AND r.type = :reputationType
                """, Reputation.class)
                .setParameter("reputationType", type)
                .setParameter("question", questionId)
                .setParameter("sender", senderId));
    }
}
