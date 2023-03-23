package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.List;

public interface PaginationDtoDao<T> {
    <P> List<P> getItems(P param);

    <P> int getTotalResultCount(P param);
}
