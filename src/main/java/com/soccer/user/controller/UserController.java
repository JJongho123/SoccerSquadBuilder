package com.soccer.user.controller;

import com.soccer.user.entity.User;
import com.soccer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
//        return userService.getAllUsers();
        return null;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
//        return userService.getUserById(id);
        return null;
    }
}
