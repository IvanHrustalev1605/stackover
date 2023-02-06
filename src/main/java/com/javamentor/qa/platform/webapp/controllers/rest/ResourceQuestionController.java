package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;
    private final QuestionService questionService;

    ResourceQuestionController(QuestionDtoService questionDtoService,
                               QuestionService questionService) {
        this.questionDtoService = questionDtoService;
        this.questionService = questionService;
    }

    @GetMapping("/{questionId}")
    @ApiOperation("api возвращает QuestionDto по id вопроса")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вопрос найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Вопрос c данным id не существует"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Вопрос не найден"
            )
    })

    public ResponseEntity<QuestionDto> getQuestionDto(@PathVariable("questionId") long questionId,
                                                      @AuthenticationPrincipal User user) {
        if (!questionService.existsById(questionId)) {
            return ResponseEntity.badRequest().build();
        }

        return questionDtoService.getById(questionId, 1L)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
