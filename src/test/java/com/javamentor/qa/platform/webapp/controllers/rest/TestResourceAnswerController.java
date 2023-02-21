package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.config.BaseTest;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestResourceAnswerController extends BaseTest {
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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answers)));
    }

    @Test
    public void getAllAnswers_AnswersNotFound_ShouldReturn404Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willThrow(new NotFoundException("Answers not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getAllAnswers_QuestionIdDoesNotExist_ShouldReturn400Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceAnswerController/addComment.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addComment_CommentAdded_ShouldReturnCommentAnswerDto() {
        String token = getToken("user@mail.test", "password");

        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/comment", 100, 100)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("comment text").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.answerId", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is("comment text")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageLink", Matchers.is("user image link")));
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceAnswerController/addComment.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addComment_BlankComment_ShouldReturn400Status() {
        String token = getToken("user@mail.test", "password");

        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/comment", 100, 100)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceAnswerController/addComment.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addComment_AnswerDoesNotExist_ShouldReturn404Status() {
        String token = getToken("user@mail.test", "password");

        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/comment", 100, 200)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("comment text").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
