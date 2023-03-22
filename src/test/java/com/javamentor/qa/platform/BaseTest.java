package com.javamentor.qa.platform;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.jwt.JwtService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@DBRider
@DBUnit
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    @Autowired
    private MockMvc mockMvc;

    public String getTokenJWT(User user) {
        return JwtService.generateToken(user);
    }


}
