package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface QuestionService extends ReadWriteService<Question, Long> {
    Optional<QuestionDto> createQuestion(QuestionCreateDto questionCreateDto, User user);
    Long getCountQuestionByUser(Long userId);
}
