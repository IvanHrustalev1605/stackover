package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.config.BaseTest;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class TestIntegrationAuthenticationController extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Successful execution of the method AuthenticationController#authentication(AuthenticationRequestDTO request)
     * will return HTTP status 200 (Success) and AuthenticationRequestDTO with value "role" = "ROLE_USER", token
     * @throws Exception
     */
    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/authentication-controller/authentication_controller_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void authentication_Success_IntegrationTest() throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("user@mail.ru", "user");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_USER")));
    }

    /**
     * Unsuccessful execution of the method AuthenticationController#authentication(AuthenticationRequestDTO request)
     * will return HTTP status 401 (Unauthorized)
     * @throws Exception
     */
    @Test
    @DataSet(cleanBefore = true,
            value = "datasets/authentication-controller/authentication_controller_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void authentication_Unsuccessful_IntegrationTest() throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("user@mail.ru", "password");
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
