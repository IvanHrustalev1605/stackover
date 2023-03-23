package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {
    private  final VoteQuestionDao voteQuestionDao;
    private  final QuestionService questionService;
    private  final ReputationService reputationService;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao, QuestionService questionService, ReputationService reputationService) {
            super(readWriteDao);
            this.voteQuestionDao = voteQuestionDao;
            this.reputationService = reputationService;
            this.questionService = questionService;
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
