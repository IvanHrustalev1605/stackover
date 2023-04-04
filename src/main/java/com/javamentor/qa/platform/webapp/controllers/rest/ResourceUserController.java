package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.PageDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final PageDtoService pageDtoService;

    public ResourceUserController(UserDtoService userDtoService, UserService userService, PageDtoService pageDtoService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.pageDtoService = pageDtoService;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Получение UserDTO через ID пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UserDTO успешно получен."),
            @ApiResponse(code = 400, message = "Неправильный запрос.")})
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable("id") Long id) {
        Optional<UserDto> user = userDtoService.getById(id);
        return user.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new UserDto(), HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{itemsOnPage}/{currentPage}")
    @ApiOperation(value = "получение всех пользователей с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "PageDto успешно получен."),
            @ApiResponse(code = 400, message = "Неправильный запрос.")})
    public PageDto<UserDto> getUsersWithPagination(@PathVariable("itemsOnPage") Long itemsOnPage, @PathVariable("currentPage") Long currentPage){
        Optional<UserDto> user = userDtoService.getById(currentPage);
        return pageDtoService.createPageDto(itemsOnPage,currentPage);
    }
}
