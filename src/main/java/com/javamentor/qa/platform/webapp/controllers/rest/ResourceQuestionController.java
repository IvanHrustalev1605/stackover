package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.model.ReputationServiceImpl;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import com.javamentor.qa.platform.service.impl.model.VoteQuestionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/question/")
public class ResourceQuestionController {
    private final VoteQuestionService voteQuestionService;
    private final ReputationService reputationService;
    private final UserService userService;

    public ResourceQuestionController(VoteQuestionServiceImpl voteQuestionService, ReputationServiceImpl reputationService, UserServiceImpl userService) {
        this.voteQuestionService = voteQuestionService;
        this.reputationService = reputationService;
        this.userService = userService;
    }

    @PostMapping("/{questionId}/upVote")
    public ResponseEntity<Long> voteAndGiveRep(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user) {

        return new ResponseEntity<>( voteQuestionService.voteUp(questionId, userService.getByEmail("user@mail.com").get()),HttpStatus.OK);
    }
}