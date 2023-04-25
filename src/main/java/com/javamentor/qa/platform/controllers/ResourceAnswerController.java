package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer/{answerId}")
@Tag(name="Контроллер ответов", description="Взаимодействие с ответами")
public class ResourceAnswerController {

    private final AnswerService answerService;

    public ResourceAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @DeleteMapping("/answerId")
    @ApiOperation(value = "Отмечает ответ для дальнейшего удаления")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно! Ответ отмечен для удаления!"),
            @ApiResponse(code = 404, message = "Ответ не найден...")
    })
    public ResponseEntity<HttpStatus> markAnswerForDelete(@Parameter(description = "Идентификатор ответа", required = true) @PathVariable Long answerId) {
        Optional<Answer> answerForMarkOptional = answerService.getAnswerById(answerId);
        if (answerForMarkOptional.isPresent()) {
            answerService.markAnswerIsDelete(answerForMarkOptional);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
