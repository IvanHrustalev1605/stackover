package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/count")
    @ApiOperation(value = "Получаем количество вопросов в базе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Количество вопросов успешно получено"),
            @ApiResponse(code = 400, message = "Неправильный запрос")
    })
    public ResponseEntity<Long> getCountQuestion() {
        Optional<Long> count = questionService.getCountQuestion();
        return count.map(aLong -> new ResponseEntity<>(aLong, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
