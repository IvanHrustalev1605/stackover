package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;

    private final QuestionService questionService;

    private final AnswerDtoDao answerDtoDao;

    public ResourceAnswerController(AnswerService answerService, QuestionService questionService, AnswerDtoDao answerDtoDao) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.answerDtoDao = answerDtoDao;
    }

    @DeleteMapping(value = "/{answerId}")
    @Operation(summary = "Помечает ответ на удаление")
    @ApiResponse(responseCode = "200", description = "Вопрос успешно помечен на удаление")
    @ApiResponse(responseCode = "403", description = "Вопрос не найден")
    public ResponseEntity<?> markAnswerToDelete(@PathVariable("answerId") Long answerId) {
        if (answerService.getById(answerId).isPresent()) {
            answerService.markAnswerAsDelete(answerId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/{questionId}")
    @Operation(summary = "Ответ по id вопроса")
    @ApiResponse(responseCode = "200", description = "Ответ найден")
    @ApiResponse(responseCode = "403", description = "Ответ не найден")
    public ResponseEntity<AnswerDto> getAllAnswer(@AuthenticationPrincipal User userId,
                                                  @PathVariable("questionId") Long questionId) {

        List<AnswerDto> answerDto = answerDtoDao.getAllAnswerDtoQuestionId(userId.getId(), questionId);

        if(questionService.existsById(questionId)) {
            return new ResponseEntity<AnswerDto>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }







}
