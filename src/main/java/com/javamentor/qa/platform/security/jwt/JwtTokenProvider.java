package com.javamentor.qa.platform.security.jwt;

import com.javamentor.qa.platform.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validInMilleSec;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validInMilleSec);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuth(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException("JWT token is expired or invalid");
        }
    }
}
