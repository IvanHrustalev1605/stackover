package com.javamentor.qa.platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class registerController {
    @GetMapping("/regpage")
    String registration() {
        return "regpage";
    }
}