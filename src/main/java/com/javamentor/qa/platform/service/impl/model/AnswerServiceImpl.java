package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;

    private final AnswerDtoDao answerDtoDao;


    @Override
    public List<AnswerDto> getAllAnswerDtoQuestionId(Long userId, Long questionId) {
        return answerDtoDao.getAllAnswerDtoQuestionId(userId, questionId);
    }

    @Override
    public void markAnswerAsDelete(Long answerId) {
        Optional<Answer> deletedAnswer = answerDao.getById(answerId);
        if (deletedAnswer.isPresent()) {
            deletedAnswer.get().setIsDeleted(true);
            answerDao.update(deletedAnswer.get());


        }

    }

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao, AnswerDtoDao answerDtoDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
        this.answerDtoDao = answerDtoDao;
    }
}

