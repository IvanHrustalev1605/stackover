package com.javamentor.qa.platform.dao.abstracts.dto.pagination;

import java.util.List;
import java.util.Map;

public interface PaginationDto<T> {

    int getCountOfAllItems(Map<String, Object> parameters);
    List<T> getItems(Map<String, Object> parameters);

}
