package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestResourceTagController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgnoredTagDtoService ignoredTagDtoService;

    @MockBean
    private TrackedTagDtoService trackedTagDtoService;

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

    @Test
    public void getAllTrackedTagDtoOfAuthorizedUser_Test() throws Exception {
        TrackedTagDto trackedTag1 = new TrackedTagDto(1L, "spring");
        TrackedTagDto trackedTag2 = new TrackedTagDto(2L, "docker");

        List<TrackedTagDto> trackedTagDtoList = List.of(trackedTag1, trackedTag2);

        ObjectMapper objectMapper = new ObjectMapper();

        given(this.trackedTagDtoService.getAllByUserId(anyLong())).willReturn(trackedTagDtoList);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/tag/tracked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(trackedTagDtoList)));
    }
}