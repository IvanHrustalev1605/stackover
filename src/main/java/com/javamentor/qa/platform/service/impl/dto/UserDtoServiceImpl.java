package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDtoServiceImpl extends ReadWriteServiceImpl<UserDto, Long> implements UserDtoService {

    private final UserDtoDao userDtoDao;
    private final TagDtoService tagDtoService;

    public UserDtoServiceImpl(ReadWriteDao<UserDto, Long> readWriteDao,
                              UserDtoDao userDtoDao,
                              TagDtoService tagDtoService) {
        super(readWriteDao);
        this.userDtoDao = userDtoDao;
        this.tagDtoService = tagDtoService;
    }


    @Override
    public UserDto getUserDtoById(Long id) {
        UserDto userDto = userDtoDao.getById(id).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с таким id не найден!")
        );

        userDto.setListTop3TagDto(tagDtoService.getTop3TagsByUserId(id));

        return userDto;
    }
}