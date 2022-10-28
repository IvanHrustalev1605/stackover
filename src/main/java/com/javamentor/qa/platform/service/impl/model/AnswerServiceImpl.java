package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {
    private final AnswerDao answerDao;

    private final ReputationDao reputationDao;

    private final VoteAnswerDao voteAnswerDao;

    private final UserDao userDao;

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao, ReputationDao reputationDao, VoteAnswerDao voteAnswerDao, UserDao userDao) {
        super(answerDao);
        this.answerDao = answerDao;
        this.reputationDao = reputationDao;
        this.voteAnswerDao = voteAnswerDao;
        this.userDao = userDao;
    }


    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao, ReputationDao reputationDao, VoteAnswerDao voteAnswerDao, UserDao userDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
        this.reputationDao = reputationDao;
        this.voteAnswerDao = voteAnswerDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Optional<Answer> getById(Long id) {
        return answerDao.getById(id);

    }

    @Override
    @Transactional
    public Optional<Answer> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return answerDao.getByAnswerIdAndUserId(answerId, userId);
    }

    @Override
    public Long increaseVoteAnswer(Long answerId, Long userId) {
        Long votes = 0L;
        if (answerDao.getById(answerId).isPresent()) {
            if (voteAnswerDao.getByAnswerIdAndUserId(answerId, userId).isPresent()) {
                voteAnswerDao.getByAnswerIdAndUserId(answerId, userId).get().setVoteType(VoteType.UP);
                if (reputationDao.getByAnswerIdAndUserId(answerId, userId).isPresent()) {
                    Reputation currentUserReputation = reputationDao.getByAnswerIdAndUserId(answerId, userId).get();
                    currentUserReputation.setCount(currentUserReputation.getCount() + 10);
                } else {
                    Reputation currentUserReputation = new Reputation();
                    currentUserReputation.setAuthor(userDao.getById(userId).get());
                    currentUserReputation.setAnswer(answerDao.getById(answerId).get());
                    currentUserReputation.setQuestion(answerDao.getById(answerId).get().getQuestion());
                    currentUserReputation.setSender(answerDao.getById(answerId).get().getUser());
                    currentUserReputation.setPersistDate(LocalDateTime.now());
                }
            } else {
                new VoteAnswer(userDao.getById(userId).get(), answerDao.getById(answerId).get(), VoteType.UP);
            }

            for (int i=0; i<answerDao.getById(answerId).get().getVoteAnswers().size(); i++) {
                if (answerDao.getById(answerId).get().getVoteAnswers().get(i).equals(VoteType.UP)) {
                    votes++;
                } else {
                    votes--;
                }
            }
        }
        return votes;
    }



}
