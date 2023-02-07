package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;

    public ResourceQuestionController(QuestionDtoService questionDtoService) {
        this.questionDtoService = questionDtoService;
    }

    @PostMapping
    @Operation(summary = "Добавляет новый вопрос, возвращает QuestionDto")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ответ успешно добавлен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Ответ не добавлен, проверьте обязательные поля")})
    @ResponseBody
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                   @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(questionDtoService.addQuestion(questionCreateDto, user));
    }
}
