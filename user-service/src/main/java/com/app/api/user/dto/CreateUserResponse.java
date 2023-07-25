package com.app.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CreateUserResponse {
    private String email;
    private String name;
    private String userId;
    private LocalDateTime createdAt;
}
