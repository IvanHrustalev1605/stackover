package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class PaginationDtoServiceAbstract<T> implements PaginationDtoService<T> {
    private final Map<String, PaginationDtoDao<T>> paginationDaoMap;

    public PaginationDtoServiceAbstract(Map<String, PaginationDtoDao<T>> paginationDaoMap) {
        this.paginationDaoMap = paginationDaoMap;
    }

    @Override
    public PageDto<T> getPageDto(Map<String, Object> parameters) {

        String keyPagination = (String) parameters.get("workPagination");
        PaginationDtoDao<T> paginationDto = paginationDaoMap.get(keyPagination);

        PageDto<T> pageDto = new PageDto<>();

        // общее количества данных в БД
        int allItems = paginationDto.getCountOfAllItems(parameters);
        pageDto.setTotalResultCount(allItems);

        // данные из БД
        pageDto.setItems(paginationDto.getItems(parameters));

        // количество данных на странице
        pageDto.setItemsOnPage((int) parameters.get("itemsOnPage"));

        // номер страницы
        pageDto.setCurrentPageNumber((int) parameters.get("currentPage"));

        // количество страниц
        int totalPage = (allItems % (int) parameters.get("itemsOnPage") == 0 ?
                allItems / (int) parameters.get("itemsOnPage") :
                allItems / (int) parameters.get("itemsOnPage") + 1);
        pageDto.setTotalPageCount(totalPage);

        return pageDto;
    }
}
