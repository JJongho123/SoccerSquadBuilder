package ssb.soccer.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable String id) {
//        return userService.getUserById(id);
//    }

}