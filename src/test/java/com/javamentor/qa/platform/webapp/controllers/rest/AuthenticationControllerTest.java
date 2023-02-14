package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private Authentication authentication;

    @Test
    public void authentication_SuccessfulAuthentication_ShouldReturnTokenResponseDTO() throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("user@mail.ru", "user");
        Role role = new Role("ROLE_USER");
        User user = new User();

        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setFullName("User User");
        user.setNickname("U$er");
        user.setRole(role);

        String requestJson = objectMapper.writeValueAsString(request);

        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPass()))).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void authentication_AuthenticationFailed_ShouldReturn401Status() throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("user@mail.ru", "user");

        String requestJson = objectMapper.writeValueAsString(request);

        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPass()))).willThrow(BadCredentialsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
