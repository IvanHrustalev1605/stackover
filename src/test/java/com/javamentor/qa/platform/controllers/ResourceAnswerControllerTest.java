package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.*;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private VoteAnswerService voteAnswerService;

    @Test
    public void downVoteAnswer() throws Exception {

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@mail.ru");
        user.setFullName("User User");
        user.setPersistDateTime(LocalDateTime.now());
        user.setLastUpdateDateTime(LocalDateTime.now());
        user.setNickname("User");
        user.setRole(role);

        Question question = new Question();
        question.setId(1L);
        question.setTitle("title");
        question.setDescription("Description");
        question.setPersistDateTime(LocalDateTime.now());
        question.setLastUpdateDateTime(LocalDateTime.now());
        question.setUser(user);

        Answer answer = new Answer();
        answer.setId(1L);
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setVoteAnswers(Collections.emptyList());
        answer.setDateAcceptTime(LocalDateTime.now());
        answer.setUpdateDateTime(LocalDateTime.now());

        Reputation reputation = new Reputation();
        reputation.setId(1L);
        reputation.setPersistDate(LocalDateTime.now());
        reputation.setAuthor(user);
        reputation.setSender(user);
        reputation.setCount(0);
        reputation.setType(ReputationType.Answer);
        reputation.setAnswer(answer);
        reputation.setQuestion(question);

        given(answerService.getAnswerByIdAndUserId(1L, 1L)).willReturn(Optional.of(answer));
        given(voteAnswerService.downVoteAnswer(answer, user, 5, VoteType.DOWN)).willReturn(1L);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/user/question/{questionId}/answer/{id}/downVote", 1, 1)
                         //делаем запрос
                )
                .andExpect(MockMvcResultMatchers.status().isOk()) //хотим получить статус ОК
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json(Long.toString(1L)));
    }
}
