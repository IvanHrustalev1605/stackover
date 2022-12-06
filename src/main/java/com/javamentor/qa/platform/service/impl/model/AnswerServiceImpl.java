package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;

    @Autowired
    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
    }

    @Override
    public Answer markAnswerAsDelete(Question question, Long answerId) throws NullPointerException {

        Answer delettedAnswer = null;
        if (question != null) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    answer.setIsDeleted(true);
                    answerDao.update(answer);
                    delettedAnswer = answer;
                }
            }
        } else {
            throw new NullPointerException("The question must not be null");
        }
        return delettedAnswer;
    }
}
