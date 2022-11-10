package com.javamentor.qa.platform.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.util.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuestionService questionService;

    @Test
    @DataSet("question_controller.yml")
    void getCountQuestionTest() throws Exception {
        List<Question> question = questionService.getAll();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/count"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(1, question.size());
    }
}
