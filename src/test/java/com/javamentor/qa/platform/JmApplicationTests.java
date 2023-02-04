package com.javamentor.qa.platform;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.QuestionDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.webapp.configs.JmApplication;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = JmApplication.class)
class JmApplicationTests {

    @Test
    void searchNull() {
        QuestionDtoDao questionDtoDao = new QuestionDtoDaoImpl();
        Optional<QuestionDto> questionDto = Optional.of(new QuestionDto());
        Assert.assertEquals(questionDto, questionDtoDao.getById(1l, 1l));
    }

}
