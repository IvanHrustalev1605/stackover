package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteAnswer> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                            SELECT r FROM VoteAnswer r
                            WHERE r.answer.id = :answerId
                            AND r.user.id = :userId
                            """)
                        .setParameter("answerId", answerId)
                        .setParameter("userId", userId));
    }

    @Override
    public Long sumVote(Long answerId) {
        return (Long) entityManager.createQuery("""
                    SELECT VoteAnswer,
                    CASE
                        WHEN vq.voteType = 'UP' THEN 1
                        WHEN vq.voteType = 'DOWN' THEN -1
                    ELSE 0
                    END
                    FROM VoteAnswer vq where vq.answer.id = :answerId
                    """)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }


}
