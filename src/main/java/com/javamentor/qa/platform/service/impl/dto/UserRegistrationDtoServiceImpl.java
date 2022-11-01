package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserRegistrationDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationDtoServiceImpl extends ReadWriteServiceImpl<UserRegistrationDto, Long> implements UserRegistrationDtoService {

    private final UserRegistrationDtoDao userRegistrationDtoDao;

    public UserRegistrationDtoServiceImpl(ReadWriteDao<UserRegistrationDto, Long> readWriteDao, UserRegistrationDtoDao userRegistrationDtoDao) {
        super(readWriteDao);
        this.userRegistrationDtoDao = userRegistrationDtoDao;
    }

    @Override
    public boolean addRegisterUserDto(UserRegistrationDto userRegistrationDto) {
        return userRegistrationDtoDao.addRegisterUserDto(userRegistrationDto);
    }

    @Override
    public boolean verifyUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        return userRegistrationDtoDao.verifyUserRegistrationDto(userRegistrationDto);
    }
}
