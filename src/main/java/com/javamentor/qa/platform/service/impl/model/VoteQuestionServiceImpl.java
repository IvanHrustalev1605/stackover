package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final QuestionService questionService;
    private final ReputationDao reputationDao;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao, QuestionService questionService, ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.questionService = questionService;
        this.reputationDao = reputationDao;
    }


    @Override
    public void upVote(Long questionId, User user) {
        User questioner = questionService.getById(questionId).get().getUser();

        if (questioner.equals(user)) {
            throw new ApiRequestException("Пользователь не может проголосовать за свой вопрос");
        }

        VoteQuestion voteQuestionByQuestionAndUserIds = voteQuestionDao.getVoteQuestionByQuestionAndUserIds(questionId, user.getId());

        voteQuestionByQuestionAndUserIds.setVote(VoteType.UP);
        voteQuestionDao.update(voteQuestionByQuestionAndUserIds);

        voteQuestionDao.persist(new VoteQuestion(user, questionService.getById(questionId).get(),VoteType.UP));

        Optional<Reputation> reputation = reputationDao.getReputationByQuestionAndAuthor(questionId, questioner.getId(), ReputationType.Question);

        if (reputation.isEmpty()) {
            Reputation reputation1 = new Reputation();
            reputation1.setQuestion(questionService.getById(questionId).get());
            reputation1.setType(ReputationType.VoteQuestion);
            reputation1.setCount(10);
            reputation1.setAuthor(questioner);
            reputation1.setSender(user);
            reputation1.setPersistDate(LocalDateTime.now());

            reputationDao.persist(reputation1);
        }
        reputation.get().setCount(reputation.get().getCount() + 10);

    }

    @Override
    public Long getCountOfQuestion(Long questionId) {
        return voteQuestionDao.getCountOfQuestion(questionId);
    }
}
