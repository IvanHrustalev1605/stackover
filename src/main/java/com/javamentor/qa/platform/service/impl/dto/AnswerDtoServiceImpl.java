package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnswerDtoServiceImpl implements AnswerDtoService {
    private final AnswerDtoDao answerDtoDao;
    private final QuestionService questionService;

    public AnswerDtoServiceImpl(AnswerDtoDao answerDtoDao, QuestionService questionService) {
        this.answerDtoDao = answerDtoDao;
        this.questionService = questionService;
    }

    @Override
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long id, Long userId) throws NotFoundException {
        if (!questionService.existsById(id)) {
            throw new NotFoundException(String.format("Question with id = %s does not exist", id));
        }

       Optional<List<AnswerDto>> answerDtoList = answerDtoDao.getAllAnswersDtoByQuestionId(id, userId);

        if (answerDtoList.isPresent()) {
            return answerDtoList.get();
        } else {
            throw new NotFoundException("Answers not found");
        }
    }
}
