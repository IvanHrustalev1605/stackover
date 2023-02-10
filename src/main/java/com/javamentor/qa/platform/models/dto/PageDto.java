package com.javamentor.qa.platform.models.dto;


import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class PageDto<T> {
    private int totalResultCount;
    private int itemsOnPage;
    private int currentPageNumber;
    private int totalPageCount;
    private List<T> items;

    public PageDto() {
        this.items = new ArrayList<>();
    }

    public PageDto(List<T> items, int currentPageNumber, int itemsOnPage, int totalResultCount) {
        this.items = items.subList(currentPageNumber * itemsOnPage, currentPageNumber * itemsOnPage + itemsOnPage);
        this.currentPageNumber = currentPageNumber;
        this.itemsOnPage = itemsOnPage;
        this.totalResultCount = totalResultCount;
        this.totalPageCount = this.totalResultCount / this.itemsOnPage;
    }

//    Example use
//    @GetMapping("/{currentPageNumber}/{itemsOnPage}")
//    public ResponseEntity<PageDto<AnswerDto>> getPageAnswer(@PathVariable("questionId") Long questionId,
//                                                        @AuthenticationPrincipal User user,
//                                                        @PathVariable int currentPageNumber,
//                                                        @PathVariable int itemsOnPage) {
//        PageDto<AnswerDto> pageAnswerDto = new PageDto<>(
//                answerDtoService.getAllAnswersDtoByQuestionId(questionId, 1L).orElseThrow(IllegalArgumentException::new),
//                currentPageNumber, itemsOnPage, 6);
//        return new ResponseEntity<>(pageAnswerDto, HttpStatus.OK);
//    }
}


