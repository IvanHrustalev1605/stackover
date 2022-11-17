package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> getUserVoteQuestion(Long userId, Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT vote FROM VoteQuestion vote
                WHERE vote.question.id = :questionId
                AND vote.user.id = :userId
                """, VoteQuestion.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId));
    }

    @Override
    public Long getSumVoteQuestionType(Long questionId) {
        return entityManager.createQuery("""
                SELECT
                COALESCE(SUM (CASE WHEN vq.voteTypeQ = 'UP' THEN 1 WHEN vq.voteTypeQ = 'DOWN' THEN -1 END), 0)  AS sumVote
                FROM VoteQuestion vq
                WHERE vq.question.id = :questionId
                """, Long.class)
                .setParameter("questionId", questionId)
                .getResultStream().count();
    }
}
