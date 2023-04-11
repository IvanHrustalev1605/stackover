package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao) {
        super(readWriteDao);
    }
}
