package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping
@RestController
public class ResourceUserController {

    private final UserDtoDao userDtoDao;

    public ResourceUserController(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @RequestMapping("/api/user/{id}")
    ResponseEntity<Optional<UserDto>> getUserDtoById (@PathVariable long id) {
        return new ResponseEntity<>(userDtoDao.getById(id), HttpStatus.OK);
    }

}
