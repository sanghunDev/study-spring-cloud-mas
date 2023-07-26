package com.app.api.user.dto;

import com.app.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    private String email;
    private String name;
    private String userId;

    public static UserResponse of(User user) {
        return new UserResponse(user.getEmail(),user.getName(),user.getUserId());
    }

    private UserResponse(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
