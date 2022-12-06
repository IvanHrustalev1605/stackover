package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface AnswerService extends ReadWriteService<Answer, Long> {

    Answer markAnswerAsDelete(Question question, Long answerId) throws NullPointerException;
}
