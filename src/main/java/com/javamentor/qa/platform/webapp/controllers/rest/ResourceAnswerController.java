package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
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
@Tag(name="Контроллер ответов", description="Взаимодействие с ответами")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получить все ответы на вопрос", response = AnswerDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответы получены успешно"),
            @ApiResponse(responseCode = "204", description = "Не найдены ответы на переданый вопрос")
    })
    public ResponseEntity<List<AnswerDto>> getAllAnswers(
            @Parameter(description = "id вопроса", required = true)
            @PathVariable("questionId") Long questionId,
            @Parameter(description = "Текущий авторизованный пользователь")
            @AuthenticationPrincipal User user) {

        if (!questionService.existsById(questionId)) {
            return ResponseEntity.noContent().build();
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
}
