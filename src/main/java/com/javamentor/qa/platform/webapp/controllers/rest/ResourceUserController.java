package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User контроллер", description = "Операции над User'ом")
public class ResourceUserController {
    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("{id}")
    @ApiOperation("Получение юзера по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сущность успешно загружена"),
            @ApiResponse(code = 400, message = "Юзера с таким id не существует")})
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserDto()); //todo #17 дто готова
    }
}
