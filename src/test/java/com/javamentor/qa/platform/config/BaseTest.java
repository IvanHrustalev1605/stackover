package com.javamentor.qa.platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc //Включаем автоматическую настройку MockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE,
        batchedStatements = true, allowEmptyFields = true, schema = "public")
@DBRider
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public String getToken(String username, String password) throws Exception {
        var authData = new AuthenticationRequestDTO(username, password);
        var jsonBody = objectMapper.writeValueAsString(authData);

        var content = mockMvc
                .perform(
                        post("/api/auth/token")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        var responseDTO = objectMapper.readValue(content, TokenResponseDTO.class);

        return responseDTO.getToken();
    }

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void simpleTest() {
    }

}
