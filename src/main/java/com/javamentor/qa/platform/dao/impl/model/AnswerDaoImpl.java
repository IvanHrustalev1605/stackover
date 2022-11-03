package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Answer> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public Optional<Answer> getAnswerByAnswerIdAndUserId(Long answerId, Long userId) {
        {
            return SingleResultUtil.getSingleResultOrNull(
                    entityManager.createQuery("SELECT a FROM Answer as a " +
                                    "WHERE a.id = :answerId " +
                                    "and not a.user.id =:userId", Answer.class)
                            .setParameter("answerId", answerId)
                            .setParameter("userId", userId)
            );
        }
    }
}