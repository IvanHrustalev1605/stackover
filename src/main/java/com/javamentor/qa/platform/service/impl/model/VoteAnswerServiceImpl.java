package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    public final VoteAnswerDao voteAnswerDao;
    public final ReputationDao reputationDao;


    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, VoteAnswerDao voteAnswerDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    @Override
    public Long increaseVoteAnswer(Answer answer, User user, Long repCount, VoteType voteType) {
        Long votes = 10L;
        Optional<VoteAnswer> votedAnswer = voteAnswerDao.getVoteAnswerByAnswerIdAndUserId(answer.getId(), user.getId());
        if (votedAnswer.isPresent()) {
            votedAnswer.get().setVoteType(VoteType.UP);
            voteAnswerDao.update(votedAnswer.get());
        } else {
            new VoteAnswer(user, answer, VoteType.UP);
        }

        Optional<Reputation> reputation = reputationDao.getReputation(answer.getId(), user.getId(), ReputationType.Answer);
        if (reputation.isPresent()) {
            reputation.get().setCount(Math.toIntExact(repCount));
            reputationDao.update(reputation.get());
        } else {
            reputation = Optional.of(new Reputation());
            reputation.get().setAnswer(answer);
            reputation.get().setSender(user);
            reputation.get().setAuthor(answer.getUser());
            reputation.get().setType(ReputationType.Answer);
            reputation.get().setCount(Math.toIntExact(repCount));
            reputation.get().setPersistDate(LocalDateTime.now());
        }
        return votes;
    }


    @Transactional
    @Override
    public Long voteDownForAnswer(Answer answer, User user, Long repCount, VoteType voteType) {
        Optional<VoteAnswer> voteAnswer = voteAnswerDao.getVoteAnswerByAnswerIdAndUserId(answer.getId(), user.getId());
        if (voteAnswer.isPresent()) {
            voteAnswer.get().setVoteType(voteType);
            voteAnswerDao.update(voteAnswer.get());
        } else {
            voteAnswer = Optional.of(new VoteAnswer(user, answer, VoteType.DOWN));
            voteAnswer.get().setPersistDateTime(LocalDateTime.now());
            voteAnswerDao.persist(voteAnswer.get());
        }
        Optional<Reputation> reputation = reputationDao.getBySenderAndQuestion(answer.getId(), user.getId(), ReputationType.Answer);
        if (reputation.isPresent()) {
            reputation.get().setCount(Math.toIntExact(repCount));
            reputationDao.update(reputation.get());
        } else {
            reputation = Optional.of(new Reputation());
            reputation.get().setAnswer(answer);
            reputation.get().setAuthor(answer.getUser());
            reputation.get().setSender(user);
            reputation.get().setCount(Math.toIntExact(repCount));
            reputation.get().setType(ReputationType.Answer);
            reputationDao.persist(reputation.get());
        }
        return countVotes(answer.getId());
    }

    @Override
    public Long countVotes(Long answerId) {
        return voteAnswerDao.countVotes(answerId);
    }

}