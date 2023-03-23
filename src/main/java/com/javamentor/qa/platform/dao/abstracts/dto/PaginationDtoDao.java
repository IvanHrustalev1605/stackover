package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.List;
import java.util.Map;

public interface PaginationDtoDao<T> {
    int getCountOfAllItems(Map<String, Object> parameters);
    List<T> getItems(Map<String, Object> parameters);
}
