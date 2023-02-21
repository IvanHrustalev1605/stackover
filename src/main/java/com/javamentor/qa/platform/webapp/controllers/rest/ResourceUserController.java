package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ResourceUserController {

    public final UserDtoService userDtoService;

    @ApiOperation("Возвращает UserDto по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESSFULLY - Успешное получение данных"),
            @ApiResponse(code = 404, message = "NOT FOUND - Пользователь с таким id не найден")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<UserDto>> getUserDtoById(@ApiParam("id пользователя") @PathVariable("id") Long id) {
        return Optional.ofNullable(userDtoService.getUserDtoById(id))
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }
}
