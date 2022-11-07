package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final VoteQuestionService voteQuestionService;

    public ResourceQuestionController(QuestionService questionService, VoteQuestionService voteQuestionService) {
        this.questionService = questionService;
        this.voteQuestionService = voteQuestionService;
    }

    @PostMapping("/downVote")
    @Operation(summary = "голосование против вопроса")
    @ApiResponse(responseCode = "200", description = "Голос против вопроса")
    @ApiResponse(responseCode = "404", description = "Вопрос не найден")
    @ApiResponse(responseCode = "400", description = "пользователь голосует за свой вопрос")
    public ResponseEntity<Long> downVoteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        Optional<Question> optionalQuestion = questionService.getById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        voteQuestionService.downVote(user, optionalQuestion.get());
        Long sumVote = voteQuestionService.getSumVoteQuestionType(optionalQuestion.get());

        return new ResponseEntity<>(sumVote, HttpStatus.OK);
    }

    @PostMapping("/upVote")
    @Operation(summary = "Позволяет проголосовать за вопрос",
            description = "Позволяет проголосовать за вопрос, также повышает репутацию автора вопроса. Возвращает значение рейтинга вопроса.")
    @ApiResponse(responseCode = "200", description = "Успешно")
    @ApiResponse(responseCode = "404", description = "Вопрос с таким id не найден")
    @ApiResponse(responseCode = "400", description = "Пользователь голосует за свой вопрос")
    public ResponseEntity<Long> upVoteQuestion(@PathVariable @Parameter(description = "Id вопроса", in = ParameterIn.PATH) Long questionId,
                                               @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getById(questionId);
        if (question.isPresent()) {
            voteQuestionService.upVote(user, question.get());
            Long sumVote = voteQuestionService.getSumVoteQuestionType(question.get());
            return new ResponseEntity<>(sumVote, HttpStatus.OK) ;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
