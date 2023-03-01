package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface QuestionService extends ReadWriteService<Question, Long> {
}
