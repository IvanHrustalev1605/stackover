package com.javamentor.qa.platform.jwt;

import com.javamentor.qa.platform.models.entity.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expiration}")
    private Long verificationInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String login, Role role) {

        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role.getName());

        Date now = new Date();
        Date verificationDate = new Date(now.getTime() + verificationInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(verificationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

}
