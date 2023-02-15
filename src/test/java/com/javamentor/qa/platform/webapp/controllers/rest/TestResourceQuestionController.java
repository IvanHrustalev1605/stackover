package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceQuestionController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionDtoService questionDtoService;
    @MockBean
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
    public void getCountQuestion_ShouldReturnTotalNumberOfQuestions() throws Exception {
        // given
        given(questionService.getCountQuestion()).willReturn(10L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/count"))

               // then
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json("10"));
    }
}

