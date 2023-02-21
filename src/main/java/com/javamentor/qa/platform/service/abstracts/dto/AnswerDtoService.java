package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import javassist.NotFoundException;

import java.util.List;

public interface AnswerDtoService {
    List<AnswerDto> getAllAnswersDtoByQuestionId(Long id, Long userId) throws NotFoundException;
}
