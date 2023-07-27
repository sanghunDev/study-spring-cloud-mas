package com.app.api.user.service;

import com.app.api.user.dto.CreateUserRequest;
import com.app.api.user.dto.CreateUserResponse;
import com.app.api.user.dto.UserResponse;
import com.app.domain.user.entity.User;
import com.app.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {
    private final UserService userService;

    public CreateUserResponse join(CreateUserRequest userRequest) {
        User userPs = userService.registerUser(userRequest.toEntity());
        return CreateUserResponse.of(userPs);
    }

    public List<UserResponse> findAll() {
        List<User> usersPs = userService.findAll();
        return usersPs.stream().map(user -> UserResponse.of(user)).collect(Collectors.toList());
    }

    public UserResponse findByEmail(String email) {
        User userPs = userService.findByEmail(email);
        return UserResponse.of(userPs);
    }

}
