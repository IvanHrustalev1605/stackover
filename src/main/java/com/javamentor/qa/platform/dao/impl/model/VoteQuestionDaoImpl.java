package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> getUserVoteQuestion(Long userId, Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT vote from VoteQuestion vote
                         where vote.id = :questionId
                         and vote.user = :userId
                         and vote.localDateTime = (select max(localDateTime) from VoteQuestion)
                        """, VoteQuestion.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId));
    }

    @Override
    public Long getSumVoteQuestionType(Long questionId) {
        return entityManager.createQuery("""
                                            select vote
                                                    from VoteQuestion vote
                                                    where vote.vote = 'up' and vote.id = :questionId
                                                    EXCEPT
                                                    select vote
                                                    from VoteQuestion vote
                                                    where vote.vote = 'down' and vote.id = :questionId
                                            """, VoteQuestion.class)
                .setParameter("questionId", questionId)
                .getResultStream().count();
    }
}
