package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user/question")
@Tag(name = "Question", description = "rest api controller for questions")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private  final VoteQuestionService voteQuestionService;

    private final ReputationService reputationService;

    @Autowired
    public ResourceQuestionController(QuestionService questionService, VoteQuestionService voteQuestionService, ReputationService reputationService) {
        this.questionService = questionService;
        this.voteQuestionService = voteQuestionService;
        this.reputationService = reputationService;
    }

    @PostMapping
    @Operation(summary = "Новый вопрос",
            tags = "Question add",
            description = "Создание нового вопроса пользователем",
            responses = {@ApiResponse(responseCode = "200", description = "Вопрос добавлен"),
                    @ApiResponse(responseCode = "400", description = "Вопрос не добавлен, проверьте обязательные поля")
            }
    )
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                   @AuthenticationPrincipal User user) {
        return questionService.createQuestion(questionCreateDto, user)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @ApiOperation(value = "Голосование «ЗА» вопрос, возвращает общие количество голосов (сумму up vote)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Голос «ЗА» добавлен"),
            @ApiResponse(responseCode = "400", description = "Голос «ЗА» не добавлен")})
    @PostMapping("/{questionId}/upVote")
    public ResponseEntity<Long> questionUpVote(@AuthenticationPrincipal User user,
                                               @PathVariable(value = "questionId") Long questionId){

        if(questionService.getById(questionId).isPresent()) {

            voteQuestionService.getQuestionUpVote(user,questionId);

            return new ResponseEntity<>(voteQuestionService.getSumUpVote(questionId), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//
}
