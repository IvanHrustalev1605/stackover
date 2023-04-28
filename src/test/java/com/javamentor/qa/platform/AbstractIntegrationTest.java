package com.javamentor.qa.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import com.javamentor.qa.platform.webapp.controllers.rest.AuthenticationController;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JmApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource("/application-test.properties")
//@PropertySource(encoding = "UTF-8", value = "/config/application-error-message.properties")
@AutoConfigureMockMvc
@DBRider
@DBUnit(caseSensitiveTableNames = true, allowEmptyFields = true, schema = "public")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected AuthenticationController authenticationController;

    protected ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }


    public String getToken(String username, String password) throws Exception {
        AuthenticationRequestDTO correct = new AuthenticationRequestDTO(username, password);
        String json = objectMapper.writeValueAsString(correct);
        MvcResult mvcResult =
                mockMvc.perform(post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isAccepted())
                        .andReturn();
        return "Bearer " + JsonPath.read(mvcResult.getResponse().getContentAsString(), "token");
    }
}