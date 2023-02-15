package com.javamentor.qa.platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.security.jwt.TokenProvider;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class) //Включаем спринговые @Autowire, @MockBean и т.д. для JUnit.
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
    private TokenProvider tokenProvider;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    public String getToken(String username, String password) throws Exception {

        TokenResponseDTO responseDTO = new TokenResponseDTO((roleService.getByName("ROLE_USER").orElseThrow()).getName(),
                tokenProvider.createToken(userService.getByEmail(username).orElseThrow()));

        String responseJson = objectMapper.writeValueAsString(responseDTO);

        return this.mockMvc.perform(post("/api/auth/token")
                        .with(httpBasic(username, password))
                        .content(responseJson)
                        .header("Authorization", "Bearer " + responseDTO.getToken()))
                .andReturn().getResponse()
                .getHeader("Authorization").substring(7);
    }

    @Test
    @DataSet(cleanBefore = true, value = "datasets/base_test.yml")
    public void simpleTest() {

    }

}
