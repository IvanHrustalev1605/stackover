package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {
    private final AnswerDtoService answerDtoService;

    @Autowired
    public ResourceAnswerController(AnswerDtoService answerDtoService) {
        this.answerDtoService = answerDtoService;
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user) {

        Optional<List<AnswerDto>> answerDtoList = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());

        return answerDtoList.map(answerDos -> new ResponseEntity<>(answerDos, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
