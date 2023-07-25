package com.app.api.user.controller;

import com.app.api.user.dto.CreateUserRequest;
import com.app.api.user.dto.CreateUserResponse;
import com.app.api.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody CreateUserRequest userRequest) {
        CreateUserResponse createUserResponse = userInfoService.join(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }
}
