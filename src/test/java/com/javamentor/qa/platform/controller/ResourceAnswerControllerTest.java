package com.javamentor.qa.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceAnswerControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestionService questionService;
    @MockBean
    private AnswerDtoService answerDtoService;
    @Autowired
    private UserService userService;


    @Test
    @DataSet("resource_answer_controller.yml")
    void getAllAnswersTest() throws Exception {
        User user = userService.getByEmail("email@mail.com").get();
        Question question = questionService.getById(1L).get();
        this.mockMvc.perform(get("/api/user/question/1/answer")
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

}