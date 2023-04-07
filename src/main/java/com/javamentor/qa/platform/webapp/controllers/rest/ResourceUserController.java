package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.impl.dto.UserPaginationDtoServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private final UserDtoService userDtoService;
    UserPaginationDtoServiceImpl userPaginationDtoServiceImpl;

    public ResourceUserController(UserDtoService userDtoService, UserPaginationDtoServiceImpl userPaginationDtoServiceImpl) {
        this.userDtoService = userDtoService;
        this.userPaginationDtoServiceImpl = userPaginationDtoServiceImpl;
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
    @GetMapping()
    @ApiOperation(value = "получение всех пользователей с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "PageDto успешно получен."),
            @ApiResponse(code = 400, message = "Неправильный запрос.")})
    public ResponseEntity<PageDto<UserDto>> getUsersWithPagination(@RequestParam("itemsOnPage") Long itemsOnPage, @RequestParam("currentPage") Long currentPage){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("workPagination","all users");
        userMap.put("itemsOnPage", Math.toIntExact(itemsOnPage));
        userMap.put("currentPage", Math.toIntExact(currentPage));
        return userPaginationDtoServiceImpl.getPageDto(userMap)!= null
                ? new ResponseEntity<>(userPaginationDtoServiceImpl.getPageDto(userMap), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
