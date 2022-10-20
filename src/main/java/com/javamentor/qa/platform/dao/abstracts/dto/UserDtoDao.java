package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserDto;

import java.util.Optional;

public interface UserDtoDao extends ReadWriteDao<UserDto, Long> {

    @Override
    Optional<UserDto> getById(Long id);
}
