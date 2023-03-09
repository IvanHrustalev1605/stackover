package com.javamentor.qa.platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UsersController {
    @GetMapping("/usersPage")
    public String getTags() {
        return "usersPage";
    }
}
