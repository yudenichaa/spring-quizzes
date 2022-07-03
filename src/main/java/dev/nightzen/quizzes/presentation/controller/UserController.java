package dev.nightzen.quizzes.presentation.controller;

import dev.nightzen.quizzes.business.entity.User;
import dev.nightzen.quizzes.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public void register(@Valid @RequestBody User user) {
        userService.register(user);
    }
}
