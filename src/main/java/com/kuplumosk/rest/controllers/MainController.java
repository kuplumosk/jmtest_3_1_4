package com.kuplumosk.rest.controllers;

import com.kuplumosk.rest.entitys.User;
import com.kuplumosk.rest.services.UserServiceImpl;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public MainController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

//    @GetMapping("/")
//    public String index() {
//        return "login";
//    }


    @GetMapping("/")
    public String index() {
        return "test";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String showUserList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allUsers", userServiceImpl.findAllUsers());
        model.addAttribute("allRoles", userServiceImpl.findAllRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        model.addAttribute("user", userServiceImpl.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id) {
        return userServiceImpl.findById(id);
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        userServiceImpl.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String add(User user, @RequestParam("role_select") Long[] roleIds) {
        for (Long id : roleIds) {
            user.addRole(userServiceImpl.getRoleById(id));
        }
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String update(User user, @RequestParam("role_select") Long[] roleIds) {
        for (Long id : roleIds) {
            user.addRole(userServiceImpl.getRoleById(id));
        }
        userServiceImpl.updateUser(user);
        return "redirect:/admin";
    }


}
