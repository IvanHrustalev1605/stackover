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


    @Override
    public Long getSumVoteUp(Long questionId) { //считаем голоса ЗА из бд
        return entityManager.createQuery("""
                        SELECT 
                            COUNT(v)
                            FROM VoteQuestion v
                            WHERE v.question.id =:questionId AND v.vote = 'UP'
                            """, Long.class)
                .setParameter("questionId", questionId).getSingleResult();
    }


}
