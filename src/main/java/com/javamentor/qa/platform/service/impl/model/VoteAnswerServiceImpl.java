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

    private final VoteAnswerDao voteAnswerDao;
    private final ReputationDao reputationDao;

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao,
                                 VoteAnswerDao voteAnswerDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    @Override
    @Transactional
    public Long downVoteAnswer(Answer answer, User user, Integer reputationCount, VoteType voteType) {

        Optional<VoteAnswer> voteAnswer = voteAnswerDao.getVoteAnswerByAnswerAndUser(answer.getId(), user.getId());
        if (voteAnswer.isPresent()) {
            voteAnswer.get().setVoteType(voteType);
            voteAnswerDao.update(voteAnswer.get());
        } else {
            voteAnswer = Optional.of(new VoteAnswer());
            voteAnswer.get().setAnswer(answer);
            voteAnswer.get().setUser(user);
            voteAnswer.get().setVoteType(voteType);
            voteAnswerDao.persist(voteAnswer.get());
        }

        Optional<Reputation> reputation = reputationDao.getReputationByAnswerAndUser(answer.getId(), user.getId());
        if (reputation.isPresent()) {
            reputation.get().setType(ReputationType.Answer);
            reputation.get().setCount(reputationCount);
            reputationDao.update(reputation.get());
        } else {
            Reputation newReputation = new Reputation();
            newReputation.setAnswer(answer);
            newReputation.setSender(user);
            newReputation.setAuthor(answer.getUser());
            newReputation.setCount(reputationCount);
            newReputation.setType(ReputationType.Answer);
            reputationDao.persist(newReputation);
        }

        return countVotes(answer.getId());
    }

    @Transactional
    @Override
    public Long countVotes(Long answerId) {
        return voteAnswerDao.countVotes(answerId);
    }
}
