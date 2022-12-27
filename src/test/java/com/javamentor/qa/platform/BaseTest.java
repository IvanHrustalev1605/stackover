package com.javamentor.qa.platform;


import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;

@RunWith(SpringRunner.class) //Включаем спринговые @Autowire, @MockBean и т.д. для JUnit.
@AutoConfigureMockMvc //Включаем автоматическую настройку MockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//Загружаем application context нашего приложения, рандомный порт для тестов веб-слоя - хороший тон :)
@DBRider
@DBUnit(caseInsensitiveStrategy = LOWERCASE, allowEmptyFields = true)
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DataSet //Нельзя ставить аннотацию DataSet над классом, для каждого отдельного метода теста свой DataSet.
    public void simpleMethod() {

    }


}
