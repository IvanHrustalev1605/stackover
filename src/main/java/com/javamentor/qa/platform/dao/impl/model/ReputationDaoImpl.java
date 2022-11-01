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
    public Optional<Reputation> getBySenderAndQuestion(Long senderId, Long questionId, ReputationType type) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                    select rep from Reputation rep
                     where rep.type = :type
                     and rep.sender = :sender
                     and rep.question = :question
                    """)
                .setParameter("type", type)
                .setParameter("sender", senderId)
                .setParameter("question", questionId));
    }
}
