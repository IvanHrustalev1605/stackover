package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;

public interface PaginationDtoService {
    <T> PageDto<T> getPage(Class<? extends PaginationDtoDao<T>> clazz, Object params);
}
