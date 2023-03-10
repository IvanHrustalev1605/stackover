package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.model.VoteAnswerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/user/question/{questionId}/answer/{id}")
@Api(description = "Контроллер для взаимодействия с ответами ")
public class ResourceAnswerController {
    private final VoteAnswerServiceImpl voteAnswerService;
    private final AnswerService answerService;
    public ResourceAnswerController(VoteAnswerServiceImpl voteAnswerService, AnswerService answerService) {
        this.voteAnswerService = voteAnswerService;
        this.answerService = answerService;
    }

    @PostMapping("/upVote")
    @ApiResponse(responseCode = "400", description = "Не удалось проголосовать")
    @ApiResponse(responseCode = "200", description = "Удалось успешно проголосовать")
    public ResponseEntity<Long> upVote(@Parameter(description = "Уникальный id ответа")
                                       @PathVariable("id") Long answerId,
                                       @Parameter(description = "Пользователь прошедший аутентификацию")
                                       @AuthenticationPrincipal User user){
        Optional<Answer> answerOptional = answerService.getAnswerForVote(answerId, user.getId());
        return answerOptional.map(
                        answer -> new ResponseEntity<>(voteAnswerService.upVote(answer, user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}

