package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface UserDtoService extends ReadWriteService<UserDto, Long> {
    Optional<UserDto> getById(Long id);
}
