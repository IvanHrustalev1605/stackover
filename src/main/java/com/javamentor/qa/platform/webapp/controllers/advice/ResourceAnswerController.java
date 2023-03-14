package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;

    public ResourceAnswerController(AnswerService answerService) {
        this.answerService = answerService;
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
}
