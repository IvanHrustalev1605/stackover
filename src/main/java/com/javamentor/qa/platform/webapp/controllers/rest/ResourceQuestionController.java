package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question")
@AllArgsConstructor
@Api(description = "Добавление вопроса")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;
    private final QuestionService questionService;
    private final UserService userService;
    private final VoteQuestionService voteQuestionService;

    @PostMapping
    @ApiOperation(value = "Добавление вопроса")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запрос выполнен"),
            @ApiResponse(code = 400, message = "Некорректные данные")})
    public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionDtoService.createQuestion(questionCreateDto, user), HttpStatus.CREATED);
    }

    @PostMapping("/{questionId}/downVote")
    @ApiOperation(value = "Голос против вопроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Оценка вопроса уменьшена"),
            @ApiResponse(code = 400, message = "Не удалось проголосовать")
    })
    public ResponseEntity<Long> voteDownForQuestion(@PathVariable("questionId") Long questionId,
                                                    @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getQuestionForVote(questionId, user.getId());
        return question.map(
                value -> new ResponseEntity<>(voteQuestionService.voteDownForQuestion(user, value, VoteType.DOWN), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{questionId}/upVote")
    @ApiOperation(value = "Проголосовать 'за' question и вернуть сумму голосов")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Оценка вопроса увеличена"),
            @ApiResponse(code = 400, message = "Не удалось проголосовать")})
    public ResponseEntity<Long> voteAndGiveRep(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>( voteQuestionService.voteUp(questionId, userService.getByEmail(user.getUsername()).get()),HttpStatus.OK);
    }
}
