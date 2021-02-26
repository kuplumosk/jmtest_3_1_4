package com.kuplumosk.rest.controllers;

import com.kuplumosk.rest.entitys.User;
import com.kuplumosk.rest.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public MainController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/login")
    public String login2() {
        return "login";
    }

    @GetMapping("/index")
    public String showUserList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allUsers", userServiceImpl.findAllUsers());
        model.addAttribute("allRoles", userServiceImpl.findAllRoles());
        return "index";
    }
}
