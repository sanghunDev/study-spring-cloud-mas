package com.app.api.user.dto;

import com.app.domain.user.entity.User;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
public class CreateUserRequest {
    private String email;
    private String name;
    private String password;
    private String userId;

    private String encryptedPassword;

    @Builder
    public CreateUserRequest(String email, String name, String password, String userId, String encryptedPassword) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.encryptedPassword = encryptedPassword;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .userId(UUID.randomUUID().toString())
                .encryptedPassword("encryptedPassword")   //TODO 암호화 비번추가 필요
                .build();
    }
}
