package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.config.BaseTest;
import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class TestResourceTagController extends BaseTest {

    @SpyBean
    private TagService tagService;
    @MockBean
    private TrackedTagDao trackedTagDao;
    @MockBean
    private IgnoredTagDtoService ignoredTagDtoService;

    @MockBean
    private TrackedTagDtoService trackedTagDtoService;

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    @WithUserDetails("user@mail.ru")
    public void addTagToTracked_TagFound_ShouldReturnTagDto() throws Exception {

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
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    @WithUserDetails("user@mail.ru")
    public void addTagToTracked_TagDoesNotExist_ShouldReturn404Status() throws Exception {

        given(tagService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 1))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    @WithUserDetails("user@mail.ru")
    public void addTagToTracked_TagAlreadyExists_ShouldReturn400Status() throws Exception {

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag1");
        tag.setDescription("desc1");

        given(tagService.existsById(1L)).willReturn(true);
        given(trackedTagDao.existTrackedTadByUser(1L, 100L)).willReturn(true);
        given(tagService.getById(1L)).willReturn(Optional.of(tag));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{id}/tracked", 1))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    @WithUserDetails("user@mail.ru")
    public void getUserIgnoredTags_ShouldReturnListOfIgnoredTags() throws Exception {
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
    @DataSet(
            cleanBefore = true,
            value = "datasets/base_test.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    @WithUserDetails("user@mail.ru")
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

    @Test
    @DataSet(
            cleanBefore = true,
            value = "datasets/ResourceTagController/getTop3TagsUser.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void getTop3TagsUser_ShouldReturn_ListOf3Tags_WhereUserEarnedMostReputationByVotingOnAnswers() throws Exception {
        // given
        var token = getToken("user@mail.test", "password");

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                                     .get("/api/user/tag/top-3tags")
                                     .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))

                    // then
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(100)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(101)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(102)));
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceTagController/addTagToIgnored.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addTagToIgnored_TagAddedToIgnored_ShouldReturnTagDto() {
        String token = getToken("user@mail.test", "password");
        TagDto tagDto = new TagDto(100L, "Tag 0", "Tag 0 description");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{tagId}/ignored", 100).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(tagDto)));
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceTagController/addTagToIgnored.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addTagToIgnored_TagAlreadyIgnored_ShouldReturn400Status() {
        String token = getToken("user@mail.test", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{tagId}/ignored", 101).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DataSet(cleanBefore = true,
            value = "datasets/ResourceTagController/addTagToIgnored.yml",
            skipCleaningFor = {"databasechangelog", "databasechangeloglock"})
    public void addTagToIgnored_TagNotFound_ShouldReturn404Status() {
        String token = getToken("user@mail.test", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/tag/{tagId}/ignored", 200).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
