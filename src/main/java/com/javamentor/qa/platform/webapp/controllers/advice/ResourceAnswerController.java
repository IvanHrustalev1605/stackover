package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;

    public ResourceAnswerController(AnswerService answerService, AnswerDtoService answerDtoService, QuestionService questionService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
    }

    @GetMapping(value = "/{answerId}")
    @ApiOperation(value = "Помечает ответ на удаление по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ответ успешно помечен на удаление."),
            @ApiResponse(code = 400, message = "Неправильный запрос.")
    })
    public ResponseEntity<?> setDeleteAnswerToQuestionById(@PathVariable Long questionId, @PathVariable Long answerId) {
        if (answerService.existsById(answerId)) {
            answerService.setDeleteById(answerId, questionId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @ApiOperation(value = "Получает список DTO ответов по id вопроса ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список ответов DTO успешно получен"),
            @ApiResponse(code = 400, message = "Неправильный запрос")
    })
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user) {
        if (questionService.existsById(questionId)) {
            Optional<List<AnswerDto>> answerDtoList = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
            return new ResponseEntity<>(answerDtoList.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
