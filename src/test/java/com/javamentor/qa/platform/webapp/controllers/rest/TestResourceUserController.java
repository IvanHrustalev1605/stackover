package com.javamentor.qa.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.config.BaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestResourceUserController extends BaseTest {
    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/ResourceUserController/getUserDtoById.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void ResourceUserController_Successful_Test() throws Exception {
        // given
        var token = getToken("user@mail.test", "password");

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                                     .get("/api/user/{id}", 100)
                                     .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))

                    // then
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", Matchers.is("John Dow")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user@mail.test")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.linkImage", Matchers.is("john_dow.jpg")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("Moscow")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.reputation", Matchers.is(10)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.registrationDate", Matchers.is("2023-01-01T00:00:00")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.votes", Matchers.is(6)));
    }

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/ResourceUserController/getUserDtoById.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void ResourceUserController_Unsuccessful_Test() throws Exception {
        // given
        var token = getToken("user@mail.test", "password");

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                                     .get("/api/user/{id}", 105)
                                     .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))

                    // then
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
