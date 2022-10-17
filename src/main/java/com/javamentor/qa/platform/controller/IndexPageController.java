package com.javamentor.qa.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @GetMapping("index")
    String getMainPage() {
        return "index";
    }
}
