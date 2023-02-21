package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.config.BaseTest;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestResourceQuestionController extends BaseTest {
    @MockBean
    private QuestionDtoService questionDtoService;
    @SpyBean
    private QuestionService questionService;

    @Test
    public void getQuestion_QuestionFound_ShouldReturnQuestionDto() throws Exception {
        QuestionDto questionDto = new QuestionDto(1L, "Title", 1L,
                "YLL", "image", "Desc",
                10L, 10L, 2L, -1L,
                LocalDateTime.now(), LocalDateTime.now(), 1L, VoteType.DOWN);

        Optional<QuestionDto> questionOptional = Optional.of(questionDto);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        given(questionService.existsById(1L)).willReturn(true);
        given(questionDtoService.getById(1L, 1L)).willReturn(questionOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(questionDto)));
    }

    @Test
    public void getQuestionDto_QuestionDtoNotFound_ShouldReturn404Status() throws Exception {
        Optional<QuestionDto> questionDto = Optional.empty();

        given(questionService.existsById(1L)).willReturn(true);
        given(questionDtoService.getById(1L, 1L)).willReturn(questionDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getQuestionDto_QuestionIdDoesNotExist_ShouldReturn400Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/ResourceQuestionController/getCountQuestion.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void getCountQuestion_ShouldReturnTotalNumberOfQuestions() throws Exception {
        // given
        var token = getToken("user@mail.test", "password");

        // when
        when(questionService.getCountQuestion()).thenCallRealMethod();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question/count")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))

                // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("2"));
    }
}
