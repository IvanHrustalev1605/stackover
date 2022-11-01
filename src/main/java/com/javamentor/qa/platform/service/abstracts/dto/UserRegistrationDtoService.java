package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface UserRegistrationDtoService extends ReadWriteService<UserRegistrationDto, Long> {
    boolean addRegisterUserDto(UserRegistrationDto userRegistrationDto);
    boolean verifyUserRegistrationDto(UserRegistrationDto userRegistrationDto);
}
