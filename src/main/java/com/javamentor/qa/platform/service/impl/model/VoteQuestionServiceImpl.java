package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final ReputationDao reputationDao;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationDao = reputationDao;
    }

    @Override
    public void downVote(User user, Question question) {
        Optional<VoteQuestion> voteQuestionOptional = voteQuestionDao.getUserVoteQuestion(user.getId(), question.getId());
        Optional<Reputation> reputationOptional = reputationDao.getBySenderAndQuestion(user.getId(), question.getId(), ReputationType.VoteQuestion);
        if (user.equals(question.getUser())) {
            throw new VoteException("пользователь не может голосовать за свой вопрос");
        }
        if (voteQuestionOptional.isPresent() && reputationOptional.isPresent()) {
            if (voteQuestionOptional.get().getVote() == VoteType.UP) {
                voteQuestionOptional.get().setVote(VoteType.DOWN);
                reputationOptional.get().setCount(-5);
                reputationDao.update(reputationOptional.get());
                super.update(voteQuestionOptional.get());
            }
        } else {
            Optional<Reputation> newReputation = Optional.of(new Reputation());
            newReputation.get().setAuthor(question.getUser());
            newReputation.get().setSender(user);
            newReputation.get().setQuestion(question);
            newReputation.get().setCount(-5);
            newReputation.get().setType(ReputationType.VoteQuestion);
            reputationDao.persist(newReputation.get());
            super.persist(new VoteQuestion(user, question, VoteType.DOWN));
        }
    }

    @Override
    public Long getSumVoteQuestionType(Question question) {
        return voteQuestionDao.getSumVoteQuestionType(question.getId());
    }
}
