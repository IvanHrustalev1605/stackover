package com.javamentor.qa.platform.controllers;


import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceTagController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgnoredTagDtoService ignoredTagDtoService;

    @Test
    public void testGettingIgnoredTags() throws Exception{
        IgnoredTagDto ignoredTagDto1 = new IgnoredTagDto(100L, "tag1");
        IgnoredTagDto ignoredTagDto2 = new IgnoredTagDto(101L, "tag2");

        Principal user = Mockito.mock(Principal.class);
        Mockito.when(user.getName()).thenReturn("user@mail.com");

        given(ignoredTagDtoService.getIgnoredTagsByUserId(any())).willReturn(List.of(ignoredTagDto1, ignoredTagDto2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/tag/ignored").principal(user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

}
