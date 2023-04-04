package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;

import java.util.List;

public interface PageDtoService {
    PageDto<UserDto> createPageDto(Long itemsOnPage, Long currentPage);
}
