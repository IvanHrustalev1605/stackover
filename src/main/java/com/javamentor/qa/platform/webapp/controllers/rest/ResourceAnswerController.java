package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;

    public ResourceAnswerController(AnswerService answerService, VoteAnswerService voteAnswerService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
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
}
