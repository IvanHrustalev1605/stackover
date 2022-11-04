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
    public Optional<VoteAnswer> getVotedAnswerByAnswerIdAndUserId(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("" +
                                "SELECT va FROM VoteAnswer as va " +
                                "WHERE va.answer.id = :answerId " +
                                "and va.user.id =:userId", VoteAnswer.class)
                        .setParameter("answerId", answerId)
                        .setParameter("userId", userId)
        );
    }

    @Override
    public Long countVotes(Long answerId) {
        return entityManager.createQuery("select distinct count(va) from VoteAnswer va " +
                        "where va.answer.id = :answerId " +
                        "and va.voteType = 'UP'", Long.class)
                .getSingleResult();
    }
}
