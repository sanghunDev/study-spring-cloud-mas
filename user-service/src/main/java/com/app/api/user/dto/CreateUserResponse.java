package com.app.api.user.dto;

import com.app.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CreateUserResponse {
    private String email;
    private String name;
    private String userId;
    private LocalDateTime createdAt;

    public static CreateUserResponse of(User user) {
        return new CreateUserResponse(user.getEmail(),user.getName(),user.getUserId(), user.getCreateTime());
    }

    private CreateUserResponse(String email, String name, String userId, LocalDateTime createdAt) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
