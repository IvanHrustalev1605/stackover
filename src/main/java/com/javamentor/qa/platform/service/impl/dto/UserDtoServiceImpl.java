package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoDao userDtoDao;

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
        return userDtoDao.getById(id);
    }

}
