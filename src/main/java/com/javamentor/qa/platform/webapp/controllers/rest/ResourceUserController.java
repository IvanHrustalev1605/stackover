package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ResourceUserController {

    public final UserDtoService userDtoService;
    public final PaginationDtoService<UserDto> paginationDtoService;

    @ApiOperation("Возвращает UserDto по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESSFULLY - Успешное получение данных"),
            @ApiResponse(code = 404, message = "NOT FOUND - Пользователь с таким id не найден")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserDtoById(@ApiParam("id пользователя") @PathVariable("id") Long id) {
        Optional<UserDto> userDtoOptional = userDtoService.getUserDtoById(id);
        return userDtoOptional.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ApiOperation("Возвращает пагинацию всех UserDto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESSFULLY - Успешное получение данных"),
    })
    @GetMapping("")
    public ResponseEntity<PageDto<UserDto>> getAllUsersDtoWithPagination(@RequestParam(defaultValue = "10") int itemsOnPage,
                                                                         @RequestParam(defaultValue = "1") int currentPage) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("allUserDtoPage", "PaginationUserDtoDaoImpl");
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(paginationDtoService.getPageDto(parameters));
    }

}
