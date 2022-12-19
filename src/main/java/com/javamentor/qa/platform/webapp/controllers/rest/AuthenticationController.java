package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.jwt.JwtTokenProvider;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.dto.TokenResponseDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Возвращает Token")
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> authentication(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        try {
            String login = authenticationRequestDto.getLogin();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authenticationRequestDto.getPass()));
            User user = userService.getByEmail(login).orElse(null);

            if (user == null) {
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            String token = jwtTokenProvider.createToken(login, user.getRole());

            TokenResponseDto tokenResponseDto = new TokenResponseDto();
            tokenResponseDto.setRole(user.getRole().getName());
            tokenResponseDto.setToken(token);

            return ResponseEntity.ok(tokenResponseDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }

    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {

    }
}
