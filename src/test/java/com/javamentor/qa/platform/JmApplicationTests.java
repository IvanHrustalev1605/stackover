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
        QuestionDtoDao qdd = new QuestionDtoDaoImpl();
        Optional<QuestionDto> q = Optional.of(new QuestionDto());
        q = qdd.getById(1l, 1l);
        Assert.assertEquals(q, null);
    }

}
