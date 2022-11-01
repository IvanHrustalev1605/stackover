package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserRegistrationDtoDao extends ReadWriteDao<UserRegistrationDto, Long> {

    @Transactional
    boolean addRegisterUserDto(UserRegistrationDto userRegistrationDto);
    @Transactional
    boolean verifyUserRegistrationDto(UserRegistrationDto userRegistrationDto);

}
