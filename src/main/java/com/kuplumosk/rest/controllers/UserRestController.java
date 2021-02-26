package com.kuplumosk.rest.controllers;

import com.kuplumosk.rest.entitys.User;
import com.kuplumosk.rest.services.UserServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userServiceImpl.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable("id") Long id) {
        return userServiceImpl.findById(id);
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userServiceImpl.addUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userServiceImpl.updateUser(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteById(id);
    }
}
