package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
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
    public void getQuestionUpVote(User user, Long questionId) {

        User questionUser = (questionService.getById(questionId).get()).getUser();

        if (questionUser.equals(user)) {
            throw new VoteException("пользователь не может голосовать за свой вопрос");
        }

        VoteQuestion voteQuestionDb = voteQuestionDao.getByUserIdQuestionId(user.getId(), questionId);

        if (voteQuestionDb != null) {

            if (voteQuestionDao.userVotedUp(user.getId()) > 0) {
                throw new VoteException("пользователь проголосовал 'за' ранее");
            }

            voteQuestionDb.setVote(VoteType.UP);
            voteQuestionDao.update(voteQuestionDb);
        }

        voteQuestionDao.persist(new VoteQuestion(user, questionService.getById(questionId).get(), VoteType.UP));

        Optional<Reputation> reputation = reputationDao.getReputationByAuthorAndQuestion
                (questionUser.getId(), questionId, ReputationType.Question);

        if (reputation.isPresent()) {

            reputation.get().setCount(reputation.get().getCount() + 10);
        } else {

            Reputation reputation1 = new Reputation();
            reputation1.setQuestion(questionService.getById(questionId).get());
            reputation1.setType(ReputationType.VoteQuestion);
            reputation1.setSender(user);
            reputation1.setCount(10);
            reputation1.setAuthor(questionUser);
            reputation1.setPersistDate(LocalDateTime.now());

            reputationDao.persist(reputation1);

        }

    }


    @Override
    public Long getSumUpVote(Long questionId) {
        return voteQuestionDao.getSumUpVote(questionId);
    }
}
