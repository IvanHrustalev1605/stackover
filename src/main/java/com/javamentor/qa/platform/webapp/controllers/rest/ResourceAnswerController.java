package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    @Autowired
    public ResourceAnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @DeleteMapping(value = "/{answerId}")
    public ResponseEntity<Answer> delete(@PathVariable("questionId") Long questionId,
                                         @PathVariable("answerId") Long answerId) {

        Question question = questionService.getById(questionId).orElse(null);

        return new ResponseEntity<>(answerService.markAnswerAsDelete(question, answerId), HttpStatus.OK);
    }
}
