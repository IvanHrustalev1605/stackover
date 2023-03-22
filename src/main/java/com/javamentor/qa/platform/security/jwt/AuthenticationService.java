package com.javamentor.qa.platform.security.jwt;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;

    public TokenResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPass()
                )
        );
        User user = userDao.getByEmail(request.getLogin())
                .orElseThrow();
        String jwtToken = JwtService.generateToken(user);
        return TokenResponseDTO.builder()
                .token(jwtToken)
                .role(user.getRole().getName())
                .build();
    }
}