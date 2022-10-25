package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    UserService userService;
    AnswerService answerService;

    @Autowired
    public ResourceAnswerController(UserService userService, AnswerService answerService) {
        this.answerService = answerService;
        this.userService = userService;
    }

    @PostMapping("/{id}/upVote")
    private ResponseEntity<Integer> upVoteAnswer(@PathVariable("id") Long id) {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }


        Reputation reputation = new Reputation();
        reputation.setAuthor(user);

        if (answerService.getById(id).isPresent()) {
            Answer answer = answerService.getById(id).get();
            VoteAnswer voteAnswerUp = new VoteAnswer(user, answer, VoteType.UP);
            answer.getVoteAnswers().add(voteAnswerUp);
            reputation.setCount(reputation.getCount() + 10);
        }
        return new ResponseEntity<>(reputation.getCount(), HttpStatus.OK);
    }
}
