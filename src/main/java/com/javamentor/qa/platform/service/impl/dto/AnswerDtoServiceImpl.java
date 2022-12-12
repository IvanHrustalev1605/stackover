package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerDtoServiceImpl implements AnswerDtoService {

    private final AnswerDtoDao answerDtoDao;

    public AnswerDtoServiceImpl(AnswerDtoDao answerDtoDao) {
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    public List<AnswerDto> getAllAnswerDtoQuestionId(Long userId, Long questionId) {
        return answerDtoDao.getAllAnswerDtoQuestionId(userId, questionId);
    }
}
