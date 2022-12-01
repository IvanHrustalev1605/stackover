package com.javamentor.qa.platform.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/profile")
public class Profile {

    @GetMapping("")
    public String userProfile(ModelMap model) {
        return "profile";
    }
}
