package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationDtoService;

import java.util.List;
import java.util.Map;

public abstract class PaginationDtoServiceAbstract<T> implements PaginationDtoService<T> {

    private Map<String, PaginationDto<T>> paginationDaoMap;

    public PageDto<T> getPageDto(Map<String, Object> parameters) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setItems((List<T>) parameters.values());
        pageDto.setTotalResultCount(parameters.size());
        pageDto.setCurrentPageNumber(0);
        pageDto.setItemsOnPage(1);
        pageDto.setTotalPageCount(parameters.size());
        return pageDto;
    }

}
