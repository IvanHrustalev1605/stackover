package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDtoDao extends ReadWriteDao<UserDto, Long> {

    @Override
    Optional<UserDto> getById(Long id);
}
