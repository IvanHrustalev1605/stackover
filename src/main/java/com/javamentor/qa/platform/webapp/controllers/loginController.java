package com.javamentor.qa.platform.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}

