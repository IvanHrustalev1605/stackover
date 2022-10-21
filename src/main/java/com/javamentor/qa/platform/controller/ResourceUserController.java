package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadOnlyService;
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

    private final ReadOnlyService<UserDto, Long> readOnlyService;

    public ResourceUserController(ReadOnlyService<UserDto, Long> readOnlyService) {
        this.readOnlyService = readOnlyService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Dto By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User обработан успешно"),
            @ApiResponse(responseCode = "404", description = "User By Id не найден")
    })
    ResponseEntity<UserDto> getUserDtoById(@PathVariable long id) {
        if (readOnlyService.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(readOnlyService.getById(id).get(), HttpStatus.OK);
    }

}

