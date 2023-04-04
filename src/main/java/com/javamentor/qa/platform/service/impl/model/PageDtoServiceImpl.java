package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.exception.PagePaginationException;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.PageDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.stereotype.Service;

@Service
public class PageDtoServiceImpl implements PageDtoService {

    private UserService userService;

    private UserDtoService userDtoService;

    public PageDtoServiceImpl(UserService userService, UserDtoService userDtoService) {

        this.userService = userService;
        this.userDtoService = userDtoService;
    }


    @Override
    public PageDto<UserDto> createPageDto(Long itemsOnPage, Long currentPage) {

        Long  countUsers = userService.getCountUsers().
                orElseThrow(() -> new UserNotFoundException("База данных пуста"));
        userDtoService.getUserDtoItemsForPagination(itemsOnPage,currentPage).get().stream().findFirst()
                .orElseThrow(() ->
                new PagePaginationException("Значения  itemsOnPage или currentPage не соответствуют количеству пользователей в БД"));

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setItemsOnPage(Math.toIntExact(itemsOnPage));
        pageDto.setCurrentPageNumber(Math.toIntExact(currentPage));
        pageDto.setTotalPageCount((int) Math.ceil((double)userService.getCountUsers().get()/itemsOnPage));
        pageDto.setTotalResultCount(Math.toIntExact(countUsers));
        pageDto.setItems(userDtoService.getUserDtoItemsForPagination(itemsOnPage,currentPage).get());
        return pageDto;
    }
}
