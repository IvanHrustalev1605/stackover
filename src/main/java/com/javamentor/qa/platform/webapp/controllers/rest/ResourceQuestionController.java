package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponse(responseCode = "403", description = "Вопрос не найден")
    @ApiResponse(responseCode = "400", description = "пользователь голосует за свой вопрос")
    public ResponseEntity<Long> downVoteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        Optional<Question> questionOp;
        if((questionOp = questionService.getById(questionId)).isPresent()) {
            voteQuestionService.downVote(user, questionOp.get());
            return new ResponseEntity<>(voteQuestionService.getSumVoteQuestionType(questionOp.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
