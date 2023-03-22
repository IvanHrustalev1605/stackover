package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Objects;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;
    private QuestionDaoImpl questionDao;
    private ReputationService reputationService;


    public VoteQuestionDaoImpl(QuestionDaoImpl questionDao, ReputationService reputationService) {
        this.questionDao = questionDao;
        this.reputationService = reputationService;
    }

    @Override
    public Long VoteUp(Long questionId, User user) {
        if (!Objects.equals(questionDao.findQuestionById(questionId).get().getUser().getId(), user.getId())) {
            if (getByQuestionIdAndUserId(questionId, user.getId()).isEmpty()) {
                VoteQuestion voteQuestion = new VoteQuestion();
                voteQuestion.setUser(user);
                voteQuestion.setQuestion(questionDao.findQuestionById(questionId).get());
                voteQuestion.setVote(VoteType.UP);
                entityManager.persist(voteQuestion);
            }
        } else {
            throw new VoteException("Нельзя голосовать за свой ответ");
        }
        reputationService.UpReputationForQuestion(questionId, user);

        return countOfVotes(questionId, user);
    }

    @Override
    public Optional<VoteQuestion> getByQuestionIdAndUserId(Long questionId, Long userId) {
        return  SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                            SELECT r FROM VoteQuestion r 
                            WHERE r.question.id = :questionId
                            AND r.user.id = :userId
                            """)
                        .setParameter("questionId", questionId)
                        .setParameter("userId", userId));
    }

    @Override
    public Long countOfVotes(Long questionId, User user) {
        return (Long) entityManager.createQuery(""" 
                                            SELECT COALESCE(SUM(
                                            CASE
                                                WHEN vq.vote = 'UP' THEN 1
                                                WHEN vq.vote = 'DOWN' THEN -1
                                                
                                            ELSE 0
                                            END),0)
                                            FROM VoteQuestion vq where vq.question.id = :id 
                                            """)
                .setParameter("id", questionId).getSingleResult();
    }
}