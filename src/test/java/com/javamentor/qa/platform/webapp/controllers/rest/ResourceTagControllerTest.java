package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

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
}
