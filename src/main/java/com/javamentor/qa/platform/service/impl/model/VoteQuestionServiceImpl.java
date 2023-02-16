package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, QuestionService questionService, ReputationService reputationService, UserService userService, VoteQuestionDao voteQuestionDao) {
        super(readWriteDao);
        this.questionService = questionService;
        this.reputationService = reputationService;
        this.userService = userService;
        this.voteQuestionDao = voteQuestionDao;
    }
    private final QuestionService questionService;
    private final ReputationService reputationService;
    private final UserService userService;
    private final VoteQuestionDao voteQuestionDao;

    @Override
    public Long voteUpQuestion(Long userId, Long questionId) {
        //голосуем "ЗА" в вопросе
        voteQuestionDao.persist(new VoteQuestion(userService.getById(userId).get(), questionService.getById(questionId).get(), VoteType.UP));

        //надо увеличить репутацию автору
        Reputation reputation = reputationService.getReputationByVoteQuestion(userId, questionId).orElse(new Reputation()); //получаем репутации автора\создаем новую
        reputation.setCount(reputation.getCount()+10); //даем +10
        reputationService.persist(reputation);

//        //вернуть сумму voteUp в вопросе
//        return questionService.getById(questionId).get().getVoteQuestions().stream()
//                .filter(voteQuestion -> voteQuestion.getVote() == VoteType.UP).count();
//
        return getSumVoteUp(questionId);

    }

    @Override
    public Long getSumVoteUp(Long questionId) {
        return voteQuestionDao.getSumVoteUp(questionId);
    }
}

