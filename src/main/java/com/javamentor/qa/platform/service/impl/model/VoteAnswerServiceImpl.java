package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final VoteAnswerDao voteAnswerDao;
    private final ReputationService reputationService;

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, VoteAnswerDao voteAnswerDao, ReputationService reputationService) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationService = reputationService;
    }


    @Override
    public Long upVote(Answer answer, User user) {
        if (!Objects.equals((answer.getUser().getId()), user.getId())) {
            return vote(answer, user, 10, VoteType.UP);
        } else {
            throw new VoteException("Нельзя голосовать за свой ответ.");
        }
    }

    @Override
    public Long vote(Answer answer, User sender, Integer repCount, VoteType voteType) {
        Optional<VoteAnswer> voteAnswerOptional = voteAnswerDao.getByAnswerIdAndUserId(answer.getId(), sender.getId());
        Optional<Reputation> reputationOptional = reputationService.getByAnswerIdAndUserId(answer.getId(), sender.getId());
        VoteAnswer voteAnswer;
        Reputation reputation;
        if (reputationOptional.isPresent() && voteAnswerOptional.isPresent()) {
            voteAnswer = voteAnswerOptional.get();
            reputation = reputationOptional.get();
        } else {
            voteAnswer = new VoteAnswer();
            reputation = new Reputation();
        }

        voteAnswer.setAnswer(answer);
        voteAnswer.setVoteType(voteType);
        voteAnswer.setUser(sender);

        reputation.setType(ReputationType.VoteAnswer);
        reputation.setSender(sender);
        reputation.setAnswer(answer);
        reputation.setCount(repCount);
        reputation.setAuthor(answer.getUser());
        try {
            voteAnswerDao.update(voteAnswer);
            reputationService.update(reputation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voteAnswerDao.sumVote(answer.getId());
    }
}
