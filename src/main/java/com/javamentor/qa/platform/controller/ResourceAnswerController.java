package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;

    public ResourceAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @DeleteMapping("/{answerId}")
    private ResponseEntity<Object> deleteAnswerController(@PathVariable Long answerId) {
        return answerService.checkAndDeleteAnswerById(answerId);
    }
}
