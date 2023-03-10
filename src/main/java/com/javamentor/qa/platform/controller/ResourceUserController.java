package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }
    @GetMapping("{id}")
    @ApiOperation(value = "Получение UserDTO через ID пользователя.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UserDTO успешно получен."),
            @ApiResponse(code = 400, message = "Неправильный запрос.") })
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtoService.getById(id).get());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity handleException(NoResultException exception) {
        return ResponseEntity.notFound().build();
    }
}
