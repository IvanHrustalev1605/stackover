package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDtoService extends ReadWriteService<UserDto, Long> {

    Optional <UserDto> getById(Long id);

    List<UserDto> getAll();

    PageDto<UserDto> getPageDTO(Map<String, Object> param);

}
