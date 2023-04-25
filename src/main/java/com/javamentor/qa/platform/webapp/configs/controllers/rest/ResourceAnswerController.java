package com.javamentor.qa.platform.webapp.configs.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final VoteAnswerService voteAnswerService;
    private final AnswerService answerService;
    public ResourceAnswerController(VoteAnswerService voteAnswerService, AnswerService answerService) {
        this.voteAnswerService = voteAnswerService;
        this.answerService = answerService;
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation(value = "Голосование за ответ ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Голосование прошло успешно"),
            @ApiResponse(code = 400, message = "Ошибка голосования")
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
