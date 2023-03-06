package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("v1/examples")
@Api(description = "Контроллер для иллюстрации работы Swagger")
public class ExampleController {
    @GetMapping
    @ApiOperation("Получение списка всех записей")
    public List<UserDto> getAll(){
        return Collections.emptyList();
    }
    @PostMapping
    @ApiOperation("Создание новой записи")
public UserDto create (@RequestBody UserDto userDto){
        return userDto;
    }
    @PutMapping("/{id}")
    @ApiOperation("Обновление существующей записи")
public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto){
        return new UserDto();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Удаление записи")
public String delete(@PathVariable Long id){
        return "id:"+id;
    }
}
