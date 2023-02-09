package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceAnswerController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerDtoService answerDtoService;
    @MockBean
    private QuestionService questionService;

    @Test
    public void getAllAnswers_AnswersFound_ShouldReturnAnswersDtoList() throws Exception {
        AnswerDto answerDto = new AnswerDto(1L, 1L, 1L, "answer text", LocalDateTime.now(),
                false, LocalDateTime.now(), 3L, 4L, "user.png", "User", 3L, VoteType.UP);
        AnswerDto answerDto1 = new AnswerDto(1L, 2L, 1L, "answer2 text", LocalDateTime.now(),
                false, LocalDateTime.now(), 5L, 3L, "user2.png", "User2", 6L, VoteType.UP);

        List<AnswerDto> answers = List.of(answerDto, answerDto1);
        Optional<List<AnswerDto>> answersOptional = Optional.of(answers);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willReturn(answersOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answers)));
    }

    @Test
    public void getAllAnswers_AnswersNotFound_ShouldReturn404Status() throws Exception {
        Optional<List<AnswerDto>> answers = Optional.empty();

        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getAllAnswers_QuestionIdDoesNotExist_ShouldReturn400Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
