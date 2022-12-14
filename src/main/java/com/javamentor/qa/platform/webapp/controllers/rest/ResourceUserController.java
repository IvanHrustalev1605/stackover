package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @Operation(summary = "Получение пользователя по id", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable("id") Long id) {

        Optional<UserDto> userDto = userDtoService.getById(id);

        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(new UserDto(), HttpStatus.NOT_FOUND));

    }
//---------------------------------------------------
    /**
     itemsOnPage - количество данных на одной странице
     currentPageNumber - номер страницы
     */
    @ApiOperation(value = "Получение всех пользователей с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "400", description = "Users not found")})

    @GetMapping("/{itemsOnPage}/{currentPageNumber}")
    public ResponseEntity<PageDto<UserDto>> getAllUserDtoPagination
    (@PathVariable("itemsOnPage") int itemsOnPage, @PathVariable("currentPageNumber") int currentPageNumber) {

        Map<String, Object> param = new HashMap<>();

        param.put("workPagination", "allUsers");
        param.put("itemsOnPage",itemsOnPage);
        param.put("currentPageNumber", currentPageNumber);

       return new ResponseEntity<>(userDtoService.getPageDTO(param), HttpStatus.OK);
}
//---------------------------------------------------
    @GetMapping("/all")
    public ResponseEntity<List <UserDto>>  getAllUsersDto() {
        List <UserDto> userDtoList = userDtoService.getAll();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
//
}
