package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private int totalResultCount;
    private int itemsOnPage;
    private int currentPageNumber;
    private int totalPageCount;
    private List<T> items;

}


