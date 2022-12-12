package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**

 Из контроллера к нам будет прилетать Map
 private final Map<String, PaginationDto<T>> paginationDaoMap;
 которую потом используем в методе getPageDto

 */
@Service
public abstract class PaginationDtoServiceImpl <T> implements PaginationDtoService <T> {

    @Override
    public PageDto <T> getPageDto(Map<String, Object> param) {

        PaginationDto<T> paginationDto = (PaginationDto<T>) param.get("PaginationDto<T>");

        PageDto<T> pageDto = new PageDto<>();
        pageDto.setCurrentPageNumber((Integer) param.get("pageNumber"));
        pageDto.setTotalPageCount
                ((int) Math.ceil((double) paginationDto.getTotalResultCount(param)
                        / (int) param.get("pageSize")));
        pageDto.setItemsOnPage((Integer) param.get("itemsOnPage"));
        pageDto.setTotalResultCount(paginationDto.getTotalResultCount(param));
        pageDto.setItems(paginationDto.getItems(param));

        return pageDto;
    }
}
