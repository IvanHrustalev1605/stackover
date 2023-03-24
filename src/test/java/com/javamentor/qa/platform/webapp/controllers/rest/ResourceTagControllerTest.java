package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class)
class ResourceTagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgnoredTagDtoService ignoredTagDtoService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TagService tagService;

    private Long correctTagId;
    private Long wrongTagId;
    private String tagName = "CSS";
    private String tagDescr = "Cascading Style Sheets";

    @Test
    @WithUserDetails("user@mail.com")
    public void addTagToIgnoreListTest() throws Exception {

        variablesInit();

        mockMvc
        .perform(post(String.format("/api/user/tag/%d/ignored", correctTagId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(correctTagId))
        .andExpect(jsonPath("$.name").value(tagName))
        .andExpect(jsonPath("$.description").value(tagDescr))
        .andReturn();

        mockMvc
        .perform(post(String.format("/api/user/tag/%d/ignored", wrongTagId)))
        .andExpect(status().isNotFound());
    }


    private void variablesInit() {

        Long requiredTagCount = entityManager
                .createQuery("SELECT COUNT(t) FROM Tag t WHERE t.name=:tname AND t.description=:des", Long.class)
                .setParameter("tname", tagName)
                .setParameter("des", tagDescr)
                .getSingleResult();

        if (requiredTagCount == 0) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tag.setDescription(tagDescr);
            tagService.persist(tag);
        }

        correctTagId = entityManager
                .createQuery("SELECT t FROM Tag t WHERE t.name=:tname AND t.description=:des", Tag.class)
                .setParameter("tname", tagName)
                .setParameter("des", tagDescr)
                .getResultList().get(0).getId();

        wrongTagId = correctTagId;
        while (tagService.existsById(wrongTagId)) {
            wrongTagId++;
        }
    }

    @org.junit.Test
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
