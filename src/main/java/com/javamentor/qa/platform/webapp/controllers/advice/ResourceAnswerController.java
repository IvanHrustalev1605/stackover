package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {
    private final CommentAnswerService commentAnswerService;
    private final CommentAnswerDtoService commentAnswerDtoService;

    public ResourceAnswerController(CommentAnswerService commentAnswerService, CommentAnswerDtoService commentAnswerDtoService) {
        this.commentAnswerService = commentAnswerService;
        this.commentAnswerDtoService = commentAnswerDtoService;
    }

    @PostMapping(value = "/answer/{answerId}/comment")
    public ResponseEntity<Optional<CommentAnswerDto>> addCommentToAnswer (@RequestBody User user,
                                                                          @RequestBody Long answerId,
                                                                          @PathVariable String comment)
    {
        if (comment.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentAnswerService.addCommentToAnswer(user, answerId, comment);
        return new ResponseEntity<>(commentAnswerDtoService.getCommentAnswerDtoById(answerId), HttpStatus.OK);
    }

}
