package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiNotFoundException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/")
@RequiredArgsConstructor
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final VoteQuestionService voteQuestionService;

    @PostMapping("/upVote")
    @ApiOperation(value = "Проголосовать за вопрос")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сумма голосов получена успешно"),
            @ApiResponse(responseCode = "400", description = "Пользователь уже проголосовал за этот вопрос или он автор вопроса"),
            @ApiResponse(responseCode = "404", description = "Не найден вопрос")
    })
    public ResponseEntity<Long> upVoteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getById(questionId);

        if (question.isEmpty() || questionService.getById(questionId).isEmpty()) {
            throw new ApiNotFoundException("Вопрос с id %s не найден.".formatted(questionId));
        }

        voteQuestionService.upVote(questionId, user);

        return new ResponseEntity<>(voteQuestionService.getCountOfQuestion(questionId), HttpStatus.OK);

    }

}
