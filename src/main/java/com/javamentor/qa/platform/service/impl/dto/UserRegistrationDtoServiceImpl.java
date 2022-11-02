package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserRegistrationDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserRegistrationDtoServiceImpl extends ReadWriteServiceImpl<UserRegistrationDto, Long> implements UserRegistrationDtoService {

    private final UserRegistrationDtoDao userRegistrationDtoDao;

    public UserRegistrationDtoServiceImpl(ReadWriteDao<UserRegistrationDto, Long> readWriteDao, UserRegistrationDtoDao userRegistrationDtoDao) {
        super(readWriteDao);
        this.userRegistrationDtoDao = userRegistrationDtoDao;
    }

    @Override
    @Transactional
    public boolean addUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        return userRegistrationDtoDao.addUserRegistrationDto(userRegistrationDto);
    }

    @Override
    @Transactional
    public boolean verifyUserRegistrationDto(String activationCode) {
        return userRegistrationDtoDao.verifyUserRegistrationDto(activationCode);
    }

    @Override
    @Transactional
    public Optional<UserRegistrationDto> getUserRegistrationDtoByActivationCode(String activationCode) {
        return userRegistrationDtoDao.getUserRegistrationDtoByActivationCode(activationCode);
    }

}
