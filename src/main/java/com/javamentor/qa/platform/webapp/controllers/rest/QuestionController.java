package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/user/question")
public class QuestionController {

    private final CommentDtoService commentDtoService;

    @Autowired
    public QuestionController(CommentDtoService commentDtoService) {
        this.commentDtoService = commentDtoService;
    }

    @GetMapping("/{id}/comment")
    @ApiOperation(value = "Получение всех комментариев к вопросу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарии получены успешно"),
            @ApiResponse(code = 400, message = "Ошибка получения комментариев")
    })
    public ResponseEntity<List<QuestionCommentDto>> getAllQuestionComment(@Parameter(description = "Вопрос")
                                                      @PathVariable("id") Long questionId) {
        if (!commentDtoService.existsById(questionId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return commentDtoService.getAllQuestionCommentDtoById(questionId).isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(commentDtoService.getAllQuestionCommentDtoById(questionId), HttpStatus.OK);
    }
}
