package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void setDeleteById(Long answerId, Long questionId) {
        entityManager.createQuery("""
                        UPDATE Answer
                        SET is_deleted = true
                        WHERE id = :id
                        AND question_id = :question_id
                        """)
                .setParameter("id", answerId)
                .setParameter("question_id", questionId)
                .executeUpdate();

    }
}