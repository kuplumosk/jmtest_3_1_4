package com.kuplumosk.security.controllers;

import com.kuplumosk.security.entitys.User;
import com.kuplumosk.security.services.UserService;
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

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String showUserList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allRoles", userService.findAllRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id) {
        return userService.findById(id);
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String add(User user, @RequestParam("role_select") Long[] roleIds) {
        for (Long id : roleIds) {
            user.addRole(userService.getRoleById(id));
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String update(User user, @RequestParam("role_select") Long[] roleIds) {
        for (Long id : roleIds) {
            user.addRole(userService.getRoleById(id));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }


}
