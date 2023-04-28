package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.jwt.JwtTokenProvider;
import com.javamentor.qa.platform.service.abstracts.dto.TokenResponseDtoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TokenResponseDtoServiceImpl implements TokenResponseDtoService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponseDtoServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenResponseDTO getTokenResponseDto(AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getLogin(), authenticationRequestDTO.getPass()));
            String token = jwtTokenProvider.createToken((User)authentication.getPrincipal());
            return TokenResponseDTO
                    .builder()
                    .token(token)
                    .role(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ,")))
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Логин или пароль не верен");
        }
    }
}
