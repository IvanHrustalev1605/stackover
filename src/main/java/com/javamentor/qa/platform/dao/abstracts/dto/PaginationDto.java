package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.List;
import java.util.Map;

public interface PaginationDto<T> {

    //Список всех элементов
    List<T> getItems(Map<String, Object> param);

    //Общее количество данных в бд
    int getTotalResultCount(Map<String, Object> param);
}