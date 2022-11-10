package com.javamentor.qa.platform.util;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.JwtUtils;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmApplication.class)
@DBRider
@Transactional
@DBUnit(caseSensitiveTableNames = true, allowEmptyFields = true, schema = "public")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource("/config/application-test.properties")
public abstract class BaseTest {
    @Autowired
    private JwtUtils jwtUtils;

    public String getToken(User user) {
        return jwtUtils.generateJwtToken(user);
    }
}