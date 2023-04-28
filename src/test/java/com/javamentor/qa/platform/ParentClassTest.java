package com.javamentor.qa.platform;

import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.jwt.JwtTokenProvider;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
public abstract class ParentClassTest {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    public String getAuthToken(AuthenticationRequestDTO authDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPass()));
        return jwtTokenProvider.createToken((User)authenticate.getPrincipal());
    }
}
