package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class TestQuestionController extends BaseTest {

    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/TestQuestionController/TestQuestionController.yml")
    void getAllCommentsOnQuestion() throws Exception {

        String token = getTokenJWT("userTest@mail.com", "password");

        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user/question/{id}/comment", 100)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].comment_id").value(100))
                .andExpect(jsonPath("$[0].question_id").value(100))
                .andExpect(jsonPath("$[0].lastRedactionDate").value("2023-03-26T00:00:00"))
                .andExpect(jsonPath("$[0].persistDate").value("2023-03-26T00:00:00"))
                .andExpect(jsonPath("$[0].text").value("Text comment"))
                .andExpect(jsonPath("$[0].userId").value(100))
                .andExpect(jsonPath("$[0].imageLink").value("image"))
                .andExpect(jsonPath("$[0].reputation").value(10));

    }
}