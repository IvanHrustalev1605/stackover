package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/user")
public class ResourceAdminController {

    private final UserService userService;

    public ResourceAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @ApiOperation("Проверяем по id, имеет пользователь блокировку или нет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответ получены успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "404", description = "User с таким id не найден в базе")
    })
    public ResponseEntity<String> getCheckUserLock(@PathVariable("userId") Long id) {
        User user = userService.getById(id).get();
        String response = String.format("User with id %s, enabled status: %s", id, user.checkUserLock());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/lock/{userId}")
    @ApiOperation("Блокируем user по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User заблокирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "404", description = "User с таким id не найден в базе")
    })
    public ResponseEntity<String> lockUser(@PathVariable("userId") Long id) {
        User user = userService.getById(id).get();
        user.lockUser();
        userService.update(user);
        String response = String.format("User with id %s, locked", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/unlock/{userId}")
    @ApiOperation("Разблокировка user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User разблокирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "404", description = "User с таким id не найден в базе")
    })
    public ResponseEntity<String> unLockUser(@PathVariable("userId") Long id) {
        User user = userService.getById(id).get();
        user.unLockUser();
        userService.update(user);
        String response = String.format("User with id %s, unlocked", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
