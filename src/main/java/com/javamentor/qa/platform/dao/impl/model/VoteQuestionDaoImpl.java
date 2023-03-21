package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Objects;
@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;
    private QuestionDaoImpl questionDao;


    public VoteQuestionDaoImpl(QuestionDaoImpl questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void voteUp(Long questionId, User user) {
        if (!Objects.equals(questionDao.findQuestionById(questionId).get().getUser().getId(), user.getId())) {
            VoteQuestion voteQuestion = new VoteQuestion();
            voteQuestion.setUser(user);
            voteQuestion.setQuestion(questionDao.findQuestionById(questionId).get());
            voteQuestion.setVote(VoteType.UP);
            entityManager.persist(voteQuestion);
        } else {
            throw new VoteException("Нельзя голосовать за свой ответ");
        }
    }
    @Override
    public Long countOfVotes(Long questionId, User user) {
        return (Long) entityManager.createQuery(""" 
                                            SELECT SUM(
                                            CASE
                                                WHEN vq.vote = 'UP' THEN 1
                                                WHEN vq.vote = 'DOWN' THEN -1
                                            ELSE 0
                                            END)
                                            FROM VoteQuestion vq where vq.question.id = :id 
                                            """)
                .setParameter("id", questionId).getSingleResult();
    }
}