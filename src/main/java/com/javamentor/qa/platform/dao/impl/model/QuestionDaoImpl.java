package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Long> getCountQuestion() {
        return Optional.of( entityManager.createQuery("""
                select COUNT (*)
                from Question as q
                where q.isDeleted = false
                """,Long.class).getSingleResult());
    }
    public Optional<Question> findQuestionById(Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select r from Question r where r.id = :questionId")
                .setParameter("questionId", questionId));
    }
}
