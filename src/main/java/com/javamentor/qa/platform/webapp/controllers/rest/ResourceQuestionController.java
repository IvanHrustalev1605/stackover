package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user/question")
@AllArgsConstructor
@Api(description = "Добавление вопроса")
public class ResourceQuestionController {
    private QuestionDtoService questionDtoService;

    @PostMapping
    @ApiOperation(value = "Добавление вопроса")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запрос выполнен"),
            @ApiResponse(code = 400, message = "Некорректные данные")})
    public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionDtoService.createQuestion(questionCreateDto, user), HttpStatus.CREATED);
    }
}
