package com.javamentor.qa.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestResourceAnswerController extends BaseTest {

    @Test
    @DataSet(cleanAfter = true, value = "/datasets/ResourceAnswerController/getAllAnswersDataSet.yml")
    void getAllAnswers() throws Exception{

        String token = getTokenJWT("userTest@gmail.com", "password");

        //positive test
        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 100)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].userId").value(100))
                .andExpect(jsonPath("$[0].questionId").value(100));

        //check Not Found Status
        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 104)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }

}
