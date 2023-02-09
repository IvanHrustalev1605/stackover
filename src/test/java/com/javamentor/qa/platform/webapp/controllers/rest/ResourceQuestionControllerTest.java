package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)


class ResourceQuestionControllerTest {



    @Test
    public void addQuestion() {
        Question question = new Question();
        question.setTitle("test");
        question.setDescription("testdisc");

        List<Tag> y = new ArrayList<>();
        Tag t1 = new Tag();
        Tag t2 = new Tag();
        y.add(t1);
        y.add(t2);
        question.setTags(y);

        QuestionDto x = QuestionConverter.INSTANCE.questionToQuestionDto(question);

        assertEquals("test", x.getTitle());
        assertEquals("testdisc", x.getDescription());
//        Assertions.assertTrue(y.size() == x.getListTagDto().size());

    }
}