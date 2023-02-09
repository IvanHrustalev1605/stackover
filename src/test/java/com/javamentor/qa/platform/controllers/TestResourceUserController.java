package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceUserController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDtoService userDtoService;

    private static final UserDto TEST_USER_DTO = new UserDto(100L, "user100@mail.ru", "User100", "https://user/image.jpg", "Moscow", 100L, LocalDateTime.of(2023, 2, 8, 0, 0, 1), 20L);

    @Test
    public void ResourceUserController_Successful_Test() throws Exception {
        given(this.userDtoService.getUserDtoById(100L)).willReturn(Optional.of(TEST_USER_DTO));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/{id}", 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", Matchers.is("User100")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user100@mail.ru")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.linkImage", Matchers.is("https://user/image.jpg")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("Moscow")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registrationDate", Matchers.is("2023-02-08T00:00:01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votes", Matchers.is(20)));

    }

    @Test
    public void ResourceUserController_Unsuccessful_Test() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
