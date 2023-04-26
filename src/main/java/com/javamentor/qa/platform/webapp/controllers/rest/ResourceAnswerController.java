package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiNotFoundException;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@RequiredArgsConstructor
@Tag(name = "Контроллер ответов", description = "Взаимодействие с ответами")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получить все ответы на вопрос", response = AnswerDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответы получены успешно"),
            @ApiResponse(responseCode = "404", description = "Не найдены ответы на переданный вопрос")
    })
    public ResponseEntity<List<AnswerDto>> getAllAnswers(
            @Parameter(description = "id вопроса", required = true)
            @PathVariable("questionId") Long questionId,
            @Parameter(description = "Текущий авторизованный пользователь")
            @AuthenticationPrincipal User user) {

        if (!questionService.existsById(questionId)) {
            throw new ApiNotFoundException("Ответы на вопрос с id %s не найдены".formatted(questionId));
        }

        List<AnswerDto> answers = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
        return ResponseEntity.ok(answers);
    }

    @DeleteMapping("/answerId")
    @ApiOperation(value = "Отмечает ответ для дальнейшего удаления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно! Ответ отмечен для удаления!"),
            @ApiResponse(responseCode = "404", description = "Ответ не найден...")
    })
    public ResponseEntity<HttpStatus> markAnswerForDelete(@Parameter(description = "Идентификатор ответа", required = true) @PathVariable Long answerId) {
        Optional<Answer> answerForMarkOptional = answerService.getAnswerById(answerId);
        if (answerForMarkOptional.isPresent()) {
            answerService.markAnswerIsDelete(answerForMarkOptional);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation(value = "Голосование за ответ ")
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Голосование прошло успешно"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Ошибка голосования")
    })
    public ResponseEntity<Long> upVote(@Parameter(description = "Уникальный id ответа ")
                                       @PathVariable("questionId") Long answerId,
                                       @Parameter(description = "Пользователь ")
                                       @AuthenticationPrincipal User user) {
        Optional<Answer> answerOptional = answerService.getAnswerForVote(answerId, user.getId());
        return answerOptional.map(
                answer -> new ResponseEntity<>(voteAnswerService.upVote(answer, user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
