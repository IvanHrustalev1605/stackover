package com.javamentor.qa.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.security.jwt.JwtService;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@DBRider
@DBUnit
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    public String getTokenJWT(String email, String password) throws Exception {

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(roleService.getByName("ROLE_USER").orElseThrow().getName(),
                JwtService.generateToken(userService.getByEmail(email).orElseThrow()));

        return Objects.requireNonNull(mockMvc.perform(post("/api/auth/token")
                        .with(httpBasic(email, password))
                        .content(objectMapper.writeValueAsString(tokenResponseDTO))
                        .header("Authorization", "Bearer " + tokenResponseDTO.getToken()))
                .andReturn().getResponse()
                .getHeader("Authorization")).substring(7);
    }


}
