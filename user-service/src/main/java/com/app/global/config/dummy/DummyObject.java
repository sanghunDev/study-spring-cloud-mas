package com.app.global.config.dummy;

import com.app.domain.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObject {

    protected User newMockUser(String username) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode("1234");
        return User.builder()
                .name(username)
                .encryptedPassword(encodePassword)
                .email(username + "@naver.com")
                .build();
    }
}
