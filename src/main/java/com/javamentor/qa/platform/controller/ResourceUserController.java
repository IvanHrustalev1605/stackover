package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
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

    private final UserService userService;

    public ResourceUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Dto By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User обработан успешно"),
            @ApiResponse(responseCode = "404", description = "User By Id не найден")
    })
    ResponseEntity<User> getUserDtoById(@PathVariable Long id) {
        if (userService.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getById(id).get(), HttpStatus.OK);
    }

}

