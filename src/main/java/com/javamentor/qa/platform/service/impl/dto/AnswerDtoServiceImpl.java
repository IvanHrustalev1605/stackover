package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.impl.dto.AnswerDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerDtoServiceImpl implements AnswerDtoService {

    private final AnswerDtoDaoImpl answerDtoDao;

    public AnswerDtoServiceImpl(AnswerDtoDaoImpl answerDtoDao) {
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return answerDtoDao.getAllAnswersDtoByQuestionId(questionId, userId) ;
    }
}
