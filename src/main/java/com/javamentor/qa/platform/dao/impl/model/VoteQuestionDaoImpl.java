package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> getByQuestionIdAndUserId(Long questionId, Long userId) {
        return  SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                            SELECT r FROM VoteQuestion r 
                            WHERE r.question.id = :questionId
                            AND r.user.id = :userId
                            """)
                        .setParameter("questionId", questionId)
                        .setParameter("userId", userId));
    }

    @Override
    public Long countOfVotes(Long questionId, User user) {
        return (Long) entityManager.createQuery(""" 
                                            SELECT COALESCE(SUM(
                                            CASE
                                                WHEN vq.vote = 'UP' THEN 1
                                                WHEN vq.vote = 'DOWN' THEN -1
                                                
                                            ELSE 0
                                            END),0)
                                            FROM VoteQuestion vq where vq.question.id = :id 
                                            """)
                .setParameter("id", questionId).getSingleResult();
    }
}