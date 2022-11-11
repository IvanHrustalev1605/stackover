package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class ResourceAdminController {

    private final UserService userService;

    public ResourceAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/activation")
    @ApiOperation("Активация user по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User активирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "404", description = "User с таким id не найден в базе")
    })
    public ResponseEntity<String> activateUser(@PathVariable("userId") Long id) {
        if (userService.getById(id).isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with id %s not found", id));
        }
        userService.activateUser(id);
        String response = String.format("User with id %s, activated", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
