package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import org.springframework.http.ResponseEntity;

public interface AnswerService extends ReadWriteService<Answer, Long> {

    ResponseEntity<Object> checkAndDeleteAnswerById(Long id);
}
