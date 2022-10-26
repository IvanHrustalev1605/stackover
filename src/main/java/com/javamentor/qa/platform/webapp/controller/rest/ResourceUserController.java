package com.javamentor.qa.platform.webapp.controller.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.impl.dto.UserDtoServiceImpl;
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

    private final UserDtoServiceImpl userDtoService;

    public ResourceUserController(UserDtoServiceImpl userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Dto By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User обработан успешно"),
            @ApiResponse(responseCode = "404", description = "User By Id не найден")
    })
    private ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
        if (userDtoService.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDtoService.getById(id).get(), HttpStatus.OK);
    }

}

