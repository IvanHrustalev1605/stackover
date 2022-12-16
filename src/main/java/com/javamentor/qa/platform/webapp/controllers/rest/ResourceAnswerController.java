package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    private final VoteAnswerService voteAnswerService;

    public ResourceAnswerController(AnswerService answerService, AnswerDtoService answerDtoService, QuestionService questionService, VoteAnswerService voteAnswerService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
        this.voteAnswerService = voteAnswerService;
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
    public ResponseEntity<List<AnswerDto>> getAllAnswer(@AuthenticationPrincipal User userId,
                                                        @PathVariable("questionId") Long questionId) {
        if (questionService.getById(questionId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(answerDtoService.getAllAnswerDtoQuestionId(userId.getId(), questionId), HttpStatus.OK);
    }

    @PostMapping("/{id}/downVote")
    @Operation(summary = "Уменьшение оценки ответа и репутации")
    @ApiResponse(responseCode = "200", description = "Оценка ответа снижена")
    @ApiResponse(responseCode = "403", description = "Ответ не найден")
    public ResponseEntity<Long> downVote(@AuthenticationPrincipal User user,
                                         @PathVariable("id") Long id) {
        Optional<Answer> answer = answerService.getAnswerByAnswerIdAndUserId(id, user.getId());
        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long vote = voteAnswerService.voteDownForAnswer(answer.get(), user, 5L, VoteType.DOWN);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }
}