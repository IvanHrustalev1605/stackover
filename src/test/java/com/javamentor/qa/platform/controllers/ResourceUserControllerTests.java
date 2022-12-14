package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
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

@RunWith(SpringRunner.class) //Включаем спринговые @Autowire, @MockBean и т.д. для JUnit.
@AutoConfigureMockMvc //Включаем автоматическую настройку MockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//Загружаем application context нашего приложения, рандомный порт для тестов веб-слоя - хороший тон :)
public class ResourceUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDtoService userDtoService;

    @Test
    public void testGetAllUserDtoPagination() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/{itemsOnPage}/{currentPageNumber}", 1,1) //делаем запрос
                        .contentType(MediaType.APPLICATION_JSON)) //тип данных в запросе (необязательно)
                .andExpect(MockMvcResultMatchers.status().isOk()) //хотим получить статус ОК
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)); //хотим получить json

    }
//
}
