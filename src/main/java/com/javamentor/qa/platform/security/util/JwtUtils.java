package com.javamentor.qa.platform.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtils {


    @Value("${spring.auth.jwt.secret}")
    private String secret;

    public String generateJwtToken(User user) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withIssuedAt(new Date())
                .withIssuer("stackover")
                .withExpiresAt(expirationDate)
                .withClaim("role", user.getRole().getName())
                .withClaim("fullName", user.getFullName())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateJwtToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("stackover")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    public String getUserNameFromJwtToken(String token) {
        return JWT.decode(token).getClaim("username").asString();
    }
}
