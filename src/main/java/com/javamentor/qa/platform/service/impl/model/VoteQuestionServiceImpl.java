package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {
    private final VoteQuestionDao voteQuestionDao;
    private final ReputationService reputationService;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, ReputationService reputationService, VoteQuestionDao voteQuestionDao) {
        super(readWriteDao);
        this.reputationService = reputationService;
        this.voteQuestionDao = voteQuestionDao;
    }

    @Override
    public Long voteDownForQuestion(User user, Question question, VoteType voteType) {
        Optional<VoteQuestion> voteQuestion = voteQuestionDao.getVoteByQuestionAndByUser(question.getId(), user.getId());
        if (voteQuestion.isPresent()) {
            voteQuestion.get().setVote(voteType);
            voteQuestionDao.update(voteQuestion.get());
        } else {
            voteQuestionDao.persist(new VoteQuestion(user, question, voteType));
        }

        Optional<Reputation> reputation = reputationService.getReputationByUserId(question.getUser().getId(), ReputationType.Question);

        if (reputation.isPresent()) {
            reputation.get().setCount(reputation.get().getCount() - 5);
        } else {
            Reputation newReputation = new Reputation();
            newReputation.setQuestion(question);
            newReputation.setAuthor(question.getUser());
            newReputation.setSender(user);
            newReputation.setCount(-5);
            newReputation.setType(ReputationType.Question);
            reputationService.persist(newReputation);
        }

        return voteQuestionDao.sumVoteForQuestion(question.getId());
    }
}
