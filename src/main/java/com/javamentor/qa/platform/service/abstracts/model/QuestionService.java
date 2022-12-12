package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface QuestionService extends ReadWriteService<Question, Long> {
    QuestionDto createQuestion(QuestionCreateDto questionCreateDto, String authUserEmail);
}
