package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;

    private final VoteAnswerService voteAnswerService;

    public ResourceAnswerController(QuestionService questionService, AnswerService answerService, AnswerDtoService answerDtoService, VoteAnswerService voteAnswerService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.voteAnswerService = voteAnswerService;
    }

    @DeleteMapping("/{answerId}")
    @Operation(summary = "Помечает ответ на удаление")
    @ApiResponse(responseCode = "200", description = "Вопрос успешно помечен на удаление")
    @ApiResponse(responseCode = "403", description = "Вопрос не найден")
    public ResponseEntity<?> markAnswerToDelete(@PathVariable("answerId") Long answerId) {
        if (answerService.getById(answerId).isPresent()) {
            answerService.getById(answerId).get().setIsDeleted(Boolean.TRUE);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @ApiOperation("Возвращает лист DTO ответов по id вопроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответы получены успешно"),
            @ApiResponse(responseCode = "400", description = "Вопроса по ID не существует")
    })
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@AuthenticationPrincipal User user,
                                                         @PathVariable("questionId") Long questionId) {
        if (questionService.getById(questionId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<AnswerDto> answers = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation("Увеличение оценки ответа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оценка ответа успешно увеличена"),
            @ApiResponse(responseCode = "414", description = "Ответ не найден")
    })
    public ResponseEntity<Long> increaseVoteAnswer(@PathVariable Long answerId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Answer> answer = answerService.getByAnswerIdAndUserId(answerId, user.getId());
        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(voteAnswerService.increaseVoteAnswer(answer.get(), user, 10L, VoteType.UP), HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Long> voteDownForAnswer(@PathVariable Long answerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Answer> answer = answerService.getAnswerByAnswerIdAndUserId(answerId, user.getId());
        return answer.map(value ->
                new ResponseEntity<>(voteAnswerService.voteDownForAnswer(value, user, 5L, VoteType.DOWN), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
