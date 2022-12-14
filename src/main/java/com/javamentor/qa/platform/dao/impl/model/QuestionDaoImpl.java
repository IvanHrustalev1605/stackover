package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long getCountAllQuestionsByUserName(Long userId) {
        return (Long) entityManager.createQuery("""
                        SELECT 
                            COUNT(q)
                            from Question q
                                WHERE q.user.id =:user_id
                            """)
                .setParameter("user_id", userId).getSingleResult();
    }
}
