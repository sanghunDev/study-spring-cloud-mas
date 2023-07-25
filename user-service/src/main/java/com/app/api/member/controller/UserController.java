package com.app.api.member.controller;

import com.app.api.member.dto.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        return "";
    }
}
