package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WithUserDetails("user@mail.ru")
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceTagController {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;
    @MockBean
    private TrackedTagDao trackedTagDao;

    @Test
    public void addTrackedTag_TagFound_ShouldReturnTagDto() throws Exception {

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag1");
        tag.setDescription("desc1");

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("tag1");
        tagDto.setDescription("desc1");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        given(tagService.existsById(1L)).willReturn(true);
        given(trackedTagDao.existTrackedTadByUser(1L, 1L)).willReturn(false);
        given(tagService.getById(1L)).willReturn(Optional.of(tag));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json(objectMapper.writeValueAsString(tagDto)));
    }

    @Test
    public void notExistTag_ShouldReturn400Status() throws Exception {

        given(tagService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void trackedTagAlreadyExist_ShouldReturn404Status() throws Exception {

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag1");
        tag.setDescription("desc1");

        given(tagService.existsById(1L)).willReturn(true);
        given(trackedTagDao.existTrackedTadByUser(1L, 1L)).willReturn(true);
        given(tagService.getById(1L)).willReturn(Optional.of(tag));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
