package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override // Получаем VoteQuestion по userId и questionId
    public VoteQuestion getByUserIdQuestionId(Long userId, Long questionId) {

        return entityManager.createQuery("""
                        SELECT 
                            v
                            from VoteQuestion v
                                WHERE v.question.id =:questionId AND v.user.id =:userId
                            """, VoteQuestion.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override // "пользователь проголосовал 'за' ранее?"
    public Long userVotedUp(Long userId) {
        return entityManager.createQuery("""
                        SELECT 
                            COUNT(v)
                            from VoteQuestion v
                                WHERE v.user.id =:userId AND v.vote = 'UP'
                            """, Long.class)
                .setParameter("userId", userId).getSingleResult();
    }

    @Override
    public Long getSumUpVote(Long questionId) {

        return entityManager.createQuery("""
                        SELECT 
                            COUNT(v)
                            from VoteQuestion v
                                WHERE v.question.id =:questionId AND v.vote = 'UP'
                            """, Long.class)
                .setParameter("questionId", questionId).getSingleResult();

    }
//
}
