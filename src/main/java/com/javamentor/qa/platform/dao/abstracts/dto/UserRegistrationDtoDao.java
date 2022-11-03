package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;

import java.util.Optional;

public interface UserRegistrationDtoDao extends ReadWriteDao<UserRegistrationDto, Long> {

    Optional<UserRegistrationDto> getUserRegistrationDtoByActivationCode(String activationCode);

}
