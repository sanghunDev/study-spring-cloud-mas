package com.app.api.user.dto;

import com.app.domain.user.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return User.builder()
                .email(email)
                .name(name)
                .userId(UUID.randomUUID().toString())
                .encryptedPassword(encoder.encode(password))
                .build();
    }
}
