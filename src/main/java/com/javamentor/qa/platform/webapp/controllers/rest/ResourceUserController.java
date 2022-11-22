package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Получение пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь с userId=* получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с userId=* не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный формат введенного userId")
    })
    private ResponseEntity<UserDto> getUserDtoById(@PathVariable Long userId) throws Exception {
        if (userDtoService.getById(userId).isEmpty()) {
            throw new Exception("Пользователь c таким айди не найден");
        }
        return new ResponseEntity<>(userDtoService.getById(userId).get(), HttpStatus.OK);
    }

}

