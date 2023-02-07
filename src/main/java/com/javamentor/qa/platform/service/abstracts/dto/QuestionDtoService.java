package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;

public interface QuestionDtoService {
    QuestionDto addQuestion(QuestionCreateDto questionCreateDto, User user);
}
