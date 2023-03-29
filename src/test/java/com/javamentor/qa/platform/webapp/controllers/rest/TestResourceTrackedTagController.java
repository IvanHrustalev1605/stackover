package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TestResourceTrackedTagController extends BaseTest {

    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/TestResourceTrackedTagController/TestResourceTrackedTagController.yml")
    void addTagToTrackedTag() throws Exception {
        String token = getTokenJWT("userTest@mail.com", "password");

        getMockMvc().perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 100)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tag 1"));


    }
}