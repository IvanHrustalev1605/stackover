package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.EnumerationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/user/question")
@Tag(name = "Question", description = "rest api controller for questions")
public class ResourceQuestionController {

    private final TagService tagService;
    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public ResourceQuestionController(TagService tagService, QuestionService questionService,
                                      UserService userService) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Новый вопрос",
            tags = "Question add",
            description = "Создание нового вопроса пользователем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вопрос добавлен"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Вопрос не добавлен, проверьте обязательные поля"
                    )
            }
    )
    public ResponseEntity<QuestionDto> addQuestion(
            @Valid @RequestBody QuestionCreateDto questionCreateDto, Principal principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.createQuestion(
                        questionCreateDto,
                        principal.getName())
                );
    }
}
