package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
    }

    @Override
    public void markAnswerAsDelete(Long answerId) {
        Optional<Answer> deletedAnswer = answerDao.getById(answerId);
        if (deletedAnswer.isPresent()) {
            deletedAnswer.get().setIsDeleted(true);
            answerDao.update(deletedAnswer.get());
        }
    }

    @Override
    public Optional<Answer> getAnswerByAnswerIdAndUserId(Long answerId, Long userId) {
        return answerDao.getAnswerByAnswerIdAndUserId(answerId, userId);
    }
}

