package com.app.api.user.service;

import com.app.api.user.dto.CreateUserRequest;
import com.app.api.user.dto.CreateUserResponse;
import com.app.domain.user.entity.User;
import com.app.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {
    private final UserService userService;

    public CreateUserResponse join(CreateUserRequest userRequest) {
        User userPs = userService.registerUser(userRequest.toEntity());
        return new CreateUserResponse(userPs.getUserId(), userPs.getEmail(), userPs.getName(), userPs.getCreateTime());
    }
}
