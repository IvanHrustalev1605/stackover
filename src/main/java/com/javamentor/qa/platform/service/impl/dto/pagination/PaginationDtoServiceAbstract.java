package com.javamentor.qa.platform.service.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class PaginationDtoServiceAbstract<T> implements PaginationDtoService<T> {

    private final Map<String, PaginationDto<T>> paginationDaoMap;

    public PaginationDtoServiceAbstract(Map<String, PaginationDto<T>> paginationDaoMap) {
        this.paginationDaoMap = paginationDaoMap;
    }


    @Override
    public PageDto<T> getPageDto(Map<String, Object> parameters) {
        PageDto<T> pageDto = new PageDto<>();

        String keyPagination = (String) parameters.get("allUserDtoPage");
        PaginationDto<T> paginationDto = paginationDaoMap.get(keyPagination);

        int countOfAllItems = paginationDto.getCountOfAllItems(parameters);
        pageDto.setTotalResultCount(countOfAllItems);

        pageDto.setItems(paginationDto.getItems(parameters));

        pageDto.setItemsOnPage((int) parameters.get("itemsOnPage"));

        pageDto.setCurrentPageNumber((Integer) parameters.get("currentPage"));

        int totalPage = (countOfAllItems % (int) parameters.get("itemsOnPage") == 0 ?
                countOfAllItems / (int) parameters.get("itemsOnPage") :
                countOfAllItems / (int) parameters.get("itemsOnPage") + 1);
        pageDto.setTotalPageCount(totalPage);

        return pageDto;
    }

}
