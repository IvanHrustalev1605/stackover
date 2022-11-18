package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.QuestionCreateConverter;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question")
public class ResourceQuestionController {
    private final QuestionCreateConverter questionCreateConverter;
    private final QuestionService questionService;
    private final VoteQuestionService voteQuestionService;

    @Autowired
    public ResourceQuestionController(QuestionCreateConverter questionCreateConverter, QuestionService questionService, VoteQuestionService voteQuestionService) {
        this.questionCreateConverter = questionCreateConverter;
        this.questionService = questionService;
        this.voteQuestionService = voteQuestionService;
    }

    @PostMapping("/{questionId}/downVote")
    @ApiOperation("голосование против вопроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Голос против вопроса"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден"),
            @ApiResponse(responseCode = "400", description = "пользователь голосует за свой вопрос")
    })
    public ResponseEntity<Long> downVoteQuestion(@PathVariable Long questionId,
                                                 @AuthenticationPrincipal User user) {
        Optional<Question> optionalQuestion = questionService.getById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        voteQuestionService.downVote(user, optionalQuestion.get());
        return ResponseEntity.ok(voteQuestionService.getSumVoteQuestionType(optionalQuestion.get()));
    }

    @PostMapping
    @ApiOperation( value = "Создание вопроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос создан успешно"),
            @ApiResponse(responseCode = "400", description = "Вопрос не был создан")
    })
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionCreateDto questionCreateDto, @AuthenticationPrincipal User user) {
        Question question = questionCreateConverter.questionCreateDto(questionCreateDto);
        question.setUser(user);
        questionService.persist(question);
        QuestionDto questionDto = questionCreateConverter.questionToDto(question);
        if (questionDto.getListTagDto().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
