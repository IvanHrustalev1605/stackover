package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        super(questionDao);
        this.questionDao = questionDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getCountQuestion() {
        return questionDao.getCountQuestion();
    }

    @Override
    public Optional<Question> getQuestionForVote(Long questionId, Long userId) {
        return questionDao.getQuestionForVote(questionId, userId);
    }
    @Override
    public Optional<Question> findQuestionById(Long questionId) {
        return questionDao.findQuestionById(questionId);
    }

}
