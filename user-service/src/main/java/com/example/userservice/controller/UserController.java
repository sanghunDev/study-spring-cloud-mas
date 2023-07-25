package com.example.userservice.controller;

import com.example.userservice.util.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    final private Greeting greeting;

    @RequestMapping("/health_check")
    public String status() {
        return "UserService..ready";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }
}
