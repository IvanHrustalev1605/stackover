package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface QuestionDtoService {
    Optional<QuestionDto> getById(Long questionId, Long authorizedUserId);
    QuestionDto addQuestion(QuestionCreateDto questionCreateDto, User user);
}
