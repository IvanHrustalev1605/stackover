package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;

public interface TokenResponseDtoService {
    TokenResponseDTO getTokenResponseDto(AuthenticationRequestDTO authenticationRequestDTO);
}
