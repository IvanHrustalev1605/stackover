package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;

import java.util.Optional;

public interface QuestionDtoService {
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId);
}
