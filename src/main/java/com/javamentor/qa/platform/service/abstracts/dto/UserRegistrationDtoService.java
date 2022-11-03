package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface UserRegistrationDtoService extends ReadWriteService<UserRegistrationDto, Long> {

    Optional<UserRegistrationDto> getUserRegistrationDtoByActivationCode(String activationCode);
}
