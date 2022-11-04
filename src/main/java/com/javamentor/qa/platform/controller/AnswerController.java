package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
public class AnswerController {
    public final AnswerService answerService;

    private final VoteAnswerService voteAnswerService;

    public AnswerController(AnswerService answerService, VoteAnswerService voteAnswerService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation("Увеличение оценки ответа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оценка ответа успешно увеличена"),
            @ApiResponse(responseCode = "414", description = "Ответ не найден")
    })

    public ResponseEntity<Long> increaseVoteAnswer(@PathVariable Long answerId) {
        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }

        Optional<Answer> answer = answerService.getByAnswerIdAndUserId(answerId, user.getId());

        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(voteAnswerService.increaseVoteAnswer(answer.get(), user, 10L, VoteType.UP), HttpStatus.OK);
        }
    }
}

