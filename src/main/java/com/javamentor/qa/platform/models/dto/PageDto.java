package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**

 T - это ДТО которые нужны будут фронтам например AnswerDto, QuestionDto и т.д.

 PageDTO - Эта структура данных используется для хранения результатов поиска с разбивкой на страницы.

 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "пагинация DTO")
public class PageDto<T>{

    @Parameter(description = "Номер текущей страницы")
    private int currentPageNumber;

    @Parameter(description = "Общее количество страниц")
    private int totalPageCount;

    @Parameter(description = "Общее количество данных в бд")
    private int totalResultCount;

    @Parameter(description = "Список всех элементов")
    private List<T> items;

    @Parameter(description = "Количество элементов на странице")
    private int itemsOnPage;

}
