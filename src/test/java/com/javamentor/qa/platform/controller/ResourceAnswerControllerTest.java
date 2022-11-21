package com.javamentor.qa.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.util.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceAnswerControllerTest extends BaseTest{

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestionService questionService;
    @MockBean
    private AnswerDtoService answerDtoService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    @Autowired
    private ReputationService reputationService;


    @Test
    @DataSet("resource_answer_controller.yml")
    void getAllAnswersTest() throws Exception {
        User user = userService.getByEmail("email@mail.com").get();
        Question question = questionService.getById(1L).get();
        this.mockMvc.perform(get("/api/user/question/{questionId}/answer", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user))
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(questionService.getById(1L)).isPresent();
        answerDtoService.getAllAnswersDtoByQuestionId(1L, user.getId());
        Mockito.verify(answerDtoService, Mockito.times(1))
                .getAllAnswersDtoByQuestionId(ArgumentMatchers.eq(1L), ArgumentMatchers.eq(user.getId()));
    }

    @Test
    @DataSet(value = "datasets/answer/resource_answer_controller.yml")
    void voteDownForAnswerTest() throws Exception {
        User user = userService.getByEmail("email@mail.com").get();
        Answer answer = answerService.getById(100L).get();
        Question question = questionService.getById(100L).get();
        Reputation reputation = reputationService.getById(100L).get();
        Assertions.assertThat(answer.getVoteAnswers().stream().count()).isEqualTo(0);
        Assertions.assertThat(reputationService.getById(100L).get().getCount()).isEqualTo(0);
        this.mockMvc.perform(post("/api/user/question/{questionId}/answer/{id}/downVote", question.getId(), answer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user)))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(answerService.getById(100L).get()).isEqualTo(answer);
        Assertions.assertThat(answer.getVoteAnswers().stream().count()).isEqualTo(1);
        Assertions.assertThat(answer.getVoteAnswers().get(0).getVoteType()).isEqualTo(1);
        Assertions.assertThat(reputationService.getById(100L).get().getCount()).isEqualTo(reputation.getCount() - 5);
        Assertions.assertThat(user).isNotEqualTo(answer.getUser());
        Assertions.assertThat(answerService.getById(100L)).isPresent();
    }
}