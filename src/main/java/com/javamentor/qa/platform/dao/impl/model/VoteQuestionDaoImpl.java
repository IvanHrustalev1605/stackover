package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Long getCountOfQuestion(Long questionId) {
        return entityManager.createQuery("""
                SELECT
                 COUNT(v)
                  FROM VoteQuestion v
                   WHERE v.question.id =:questionId
                """, Long.class)
                .setParameter("questionId", questionId)
                .getSingleResult();
    }

    @Override
    public VoteQuestion getVoteQuestionByQuestionAndUserIds(Long questionId, Long userId) {
        return entityManager.createQuery("""
            SELECT
             v
              FROM VoteQuestion v
               WHERE v.question.id =:questionId AND v.user.id =:userId
            """, VoteQuestion.class)
            .setParameter("questionId", questionId)
            .setParameter("userId", userId)
            .getSingleResult();
    }
}
