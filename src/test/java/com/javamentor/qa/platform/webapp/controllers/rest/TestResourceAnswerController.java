package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmApplication.class)
public class TestResourceAnswerController extends BaseTest {

    @Test
    @DataSet(cleanBefore = true, value = "/datasets/ResourceAnswerController/getAllQuestionDataSet.yml")
    public void upVoteTest() throws Exception {
        String token = getTokenJWT("userTest@gmail.com", "password");
        getMockMvc().perform(MockMvcRequestBuilders.post("http://localhost:8080/api/user/question/{questionId}/answer/{id}/upVote")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(10));
    }
}

