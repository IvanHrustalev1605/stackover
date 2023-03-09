package com.javamentor.qa.platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TagsController {

    @GetMapping("/tag_page")
    public String getTags() {
        return "tag_page";
    }

}
