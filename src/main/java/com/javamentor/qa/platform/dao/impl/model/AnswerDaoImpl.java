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
    public Optional<Answer> getAnswerForVote(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                select r from Answer r where r.id = :answerId
                and not r.user.id = :userId
                """)
                        .setParameter("answerId", answerId)
                        .setParameter("userId", userId));
    }
    @Override
    public Optional<Answer> findAnswerById(Long answerId) {
        return getById(answerId);
    }

    @Override
    public void markAnswerIsDelete(Optional<Answer> answerOptional) {
        (answerOptional.get()).setIsDeleted(true);
    }
}