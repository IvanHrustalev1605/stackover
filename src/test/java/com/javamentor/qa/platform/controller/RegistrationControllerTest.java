package com.javamentor.qa.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.converters.UserConverter;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.EmailService;
import com.javamentor.qa.platform.util.BaseTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmailService emailService;
    @Autowired
    private UserConverter userConverter;

    private final UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
            "firstName",
            "lastName",
            "user@list.ru",
            "password",
            "activationCode"
    );

    @Test
    @DataSet("registration_controller.yml")
    void addUserRegistrationDtoTest() throws Exception {
        this.mockMvc.perform(post("/api/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(userConverter.userRegistrationDtoDtoToUser(userRegistrationDto)))
                        .content(objectMapper.writeValueAsString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(emailService, Mockito.times(1))
                .send(ArgumentMatchers.eq("user@list.ru"), ArgumentMatchers.contains("verify"), Mockito.anyString());
    }

    @Test
    @DataSet("registration_controller.yml")
    void verifyUserRegistrationDtoTest() throws Exception {
        this.mockMvc.perform(get("/api/user/registration/verify/{activationCode}", userRegistrationDto.getActivationCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(userConverter.userRegistrationDtoDtoToUser(userRegistrationDto)))
                        .content(objectMapper.writeValueAsString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}