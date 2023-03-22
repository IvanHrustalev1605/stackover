package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.security.jwt.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> authenticate(
            HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.ok("logout");
    }
}