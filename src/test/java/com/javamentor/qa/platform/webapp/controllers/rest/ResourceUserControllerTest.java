package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ResourceUserControllerTest extends BaseTest {

    @Test
    @DataSet(cleanBefore = true,
            value = " datasets/TestUserController/TestGetUserWithPagination.yml")
    void getUsersWithPagination() throws Exception {
        String token = getTokenJWT("userTest@mail.com", "password");

        getMockMvc().perform(MockMvcRequestBuilders.get("/api/user?itemsOnPage={itemsOnPage}&currentPage={currentPage}", 2,1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.items[0].id").value(100))
                .andExpect(jsonPath("$.items[0].email").value("userTest@mail.com"))
                .andExpect(jsonPath("$.items[0].fullName").value("Ivan_Ivanov"))
                .andExpect(jsonPath("$.items[0].linkImage").value("image"))
                .andExpect(jsonPath("$.items[0].city").value("Moscow"))
                .andExpect(jsonPath("$.items[0].reputation").value(20))
                .andExpect(jsonPath("$.items[0].registrationDate").value("2023-03-25T00:00:00"))
                .andExpect(jsonPath("$.items[0].votes").value(4))
                .andExpect(jsonPath("$.itemsOnPage").value(2))

                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.items[1].id").value(101))
                .andExpect(jsonPath("$.items[1].email").value("userTest2@mail.com"))
                .andExpect(jsonPath("$.items[1].fullName").value("Ivan_Ivanov2"))
                .andExpect(jsonPath("$.items[1].linkImage").value("image2"))
                .andExpect(jsonPath("$.items[1].city").value("Moscow"))
                .andExpect(jsonPath("$.items[1].reputation").value(0))
                .andExpect(jsonPath("$.items[1].registrationDate").value("2023-03-25T00:00:00"))
                .andExpect(jsonPath("$.items[1].votes").value(0))
                .andExpect(jsonPath("$.itemsOnPage").value(2));
    }
}