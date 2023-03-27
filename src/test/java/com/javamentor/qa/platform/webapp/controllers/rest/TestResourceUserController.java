package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TestResourceUserController extends BaseTest {

    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceUserController/getUserDtoById.yml")
    void getUserDtoById_ValidValues() throws Exception {

        String token = getTokenJWT("userTest@mail.com", "password");

        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user/{id}", 100)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value("Ivan_Ivanov"))
                .andExpect(jsonPath("$.email").value("userTest@mail.com"))
                .andExpect(jsonPath("$.linkImage").value("image"))
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.registrationDate").value("2023-03-25T00:00:00"))
                .andExpect(jsonPath("$.votes").value(4))
                .andExpect(jsonPath("$.reputation").value(20));

    }

    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceUserController/getUserDtoById.yml")
    void getUserDtoById_InvalidValues() throws Exception {

        String token = getTokenJWT("userTest@mail.com", "password");

        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user/{id}", 101)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }
}