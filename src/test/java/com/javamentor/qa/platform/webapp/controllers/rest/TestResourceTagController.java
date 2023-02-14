package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestResourceTagController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgnoredTagDtoService ignoredTagDtoService;

    // TODO: Переписать на интеграционный тест когда будет настроен DBRider
    @Test
    @DataSet("datasets/dataset.yml")
    void getUserIgnoredTags_ShouldReturnListOfIgnoredTags() throws Exception {
        // given
        IgnoredTagDto tag1 = new IgnoredTagDto(1L, "tag 1");
        IgnoredTagDto tag2 = new IgnoredTagDto(2L, "tag 2");
        given(ignoredTagDtoService.getIgnoredTagsByUserId(any())).willReturn(List.of(tag1, tag2));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/tag/ignored"))

               //then
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
}