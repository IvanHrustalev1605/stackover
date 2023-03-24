package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {
    private final VoteQuestionDao voteQuestionDao;
    private final ReputationService reputationService;
    private  final QuestionService questionService;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao, QuestionService questionService, ReputationService reputationService) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationService = reputationService;
        this.questionService = questionService;
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
    @Transactional
    @Override
    public Long voteUp(Long questionId, User user) {
        if (!Objects.equals(questionService.findQuestionById(questionId).get().getUser().getId(), user.getId())) {
            if (voteQuestionDao.getByQuestionIdAndUserId(questionId, user.getId()).isEmpty()) {
                VoteQuestion voteQuestion = new VoteQuestion();
                voteQuestion.setUser(user);
                voteQuestion.setQuestion(questionService.findQuestionById(questionId).get());
                voteQuestion.setVote(VoteType.UP);
                voteQuestionDao.persist(voteQuestion);
            }
        } else {
            throw new VoteException("Нельзя голосовать за свой ответ");
        }
        reputationService.UpReputationForQuestion(questionId, user);

        return voteQuestionDao.countOfVotes(questionId, user);
        }

}
