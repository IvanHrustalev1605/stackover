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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;
    private final QuestionService questionService;
    private final AnswerDtoService answerDtoService;

    public ResourceAnswerController(AnswerService answerService, VoteAnswerService voteAnswerService, QuestionService questionService, AnswerDtoService answerDtoService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.questionService = questionService;
        this.answerDtoService = answerDtoService;
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

    @PostMapping("/{id}/upVote")
    @ApiOperation(value = "Голосование за ответ ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удалось успешно проголосовать"),
            @ApiResponse(code = 400, message = "Не удалось проголосовать")
    })
    public ResponseEntity<Long> upVote(@Parameter(description = "Уникальный id ответа")
                                       @PathVariable("id") Long answerId,
                                       @Parameter(description = "Пользователь прошедший аутентификацию")
                                       @AuthenticationPrincipal User user) {
        Optional<Answer> answerOptional = answerService.getAnswerForVote(answerId, user.getId());
        return answerOptional.map(
                        answer -> new ResponseEntity<>(voteAnswerService.upVote(answer, user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
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

    @PostMapping("/{id}/downVote")
    @ApiOperation(value = "Уменьшение оценки ответа")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Оценка ответа уменьшена"),
            @ApiResponse(code = 400, message = "Не удалось проголосовать")
    })
    public ResponseEntity<Long> voteDownForAnswer(@PathVariable("id") Long answerId, @AuthenticationPrincipal User user) {
        Optional<Answer> answer = answerService.getAnswerForVote(answerId, user.getId());
        return answer.map(
                        value -> new ResponseEntity<>(voteAnswerService.voteDownForAnswer(user, value, VoteType.DOWN), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}