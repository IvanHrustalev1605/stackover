package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;
    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;

    public ResourceAnswerController(AnswerService answerService, VoteAnswerService voteAnswerService,
                                    AnswerDtoService answerDtoService, QuestionService questionService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
    }

    @GetMapping
    @ApiOperation("Получение списка ответов к вопросу по его id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответы на вопросы найдены",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnswerDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Вопроса с таким id не существует"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ответы на вопрос не найдены"
            )
    })
    ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId,
                                                  @AuthenticationPrincipal User user) {
        if (!questionService.existsById(questionId)) {
            return ResponseEntity.badRequest().build();
        }

        return answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("/{id}/downVote")
    @ApiOperation("Уменьшение оценки ответа и репутации автора ответа")
    @ApiResponse(responseCode = "200", description = "Оценка ответа снижена")
    @ApiResponse(responseCode = "404", description = "Ответ не найден")

    public ResponseEntity<Long> downVoteAnswer(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        Optional<Answer> answer = answerService.getAnswerByIdAndUserId(id, 1L);
        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long voteCount = voteAnswerService.downVoteAnswer(answer.get(), user, 5, VoteType.DOWN);
        return new ResponseEntity<>(voteCount, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    @ApiOperation("Удаление ответа по id")
    @ApiResponse(responseCode = "204", description = "Ответ успешно удален")
    @ApiResponse(responseCode = "404", description = "Ответ не найден")
    public ResponseEntity<Void> markAnswerAsDeletedById(@PathVariable Long answerId) {
        try {
            answerService.markAnswerAsDeletedById(answerId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
