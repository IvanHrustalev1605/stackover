package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/question/{questionId}/answer/{answerId}/downVote")
public class ResourseAnswerController {


    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;



    public ResourseAnswerController(AnswerService answerService, VoteAnswerService voteAnswerService) {


        this.answerService = answerService;

        this.voteAnswerService = voteAnswerService;
    }


    @PostMapping()
    public ResponseEntity<Long> voteDownForAnswer(@PathVariable Long answerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Answer> answer = answerService.getAnswerByAnswerIdAndUserId(answerId, user.getId());
        if (answer.isPresent()) {
            return new ResponseEntity<>(voteAnswerService.voteDownForAnswer(answer.get(), user, 5L, VoteType.DOWN), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}






