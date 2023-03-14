package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.model.VoteAnswerServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

private final AnswerService answerService;
private final VoteAnswerServiceImpl voteAnswerService;

public ResourceAnswerController(AnswerService answerService, VoteAnswerServiceImpl voteAnswerService) {
    this.answerService = answerService;
    this.voteAnswerService = voteAnswerService;
}

    @GetMapping(value = "/{answerId}")
    @ApiOperation(value = "Помечает ответ на удаление по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ответ успешно помечен на удаление."),
            @ApiResponse(code = 400, message = "Неправильный запрос.")
    })
    public ResponseEntity<?> setDeleteAnswerToQuestionById(@PathVariable Long questionId, @PathVariable Long answerId) {
        if (answerService.existsById(answerId)) {
            answerService.setDeleteById(answerId, questionId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation(value = "Голосование за ответ ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Удалось успешно проголосовать"),
            @ApiResponse(code = 400, message = "Не удалось проголосовать")
    })
    public ResponseEntity<Long> upVote(@Parameter(description = "Уникальный id ответа")
                                       @PathVariable("id") Long answerId,
                                       @Parameter(description = "Пользователь прошедший аутентификацию")
                                       @AuthenticationPrincipal User user) {
        Optional<Answer> answerOptional = answerService.getAnswerForVote(answerId, user.getId());
        return answerOptional.map(
                        answer -> new ResponseEntity<>(voteAnswerService.upVote(answer, user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}