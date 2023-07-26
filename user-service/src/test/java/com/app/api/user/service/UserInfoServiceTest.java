package com.app.api.user.service;

import com.app.api.user.dto.CreateUserRequest;
import com.app.domain.user.entity.User;
import com.app.domain.user.repository.UserRepository;
import com.app.domain.user.service.UserService;
import com.app.global.config.dummy.DummyObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void join() {
        // given
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("test@naver.com")
                .name("test")
                .password("1234")
                .build();

        // stub - 이메일 중복 검사 통과
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // stub - 저장 메서드가 호출 되면 정상적인 사용자 객체 반환
        when(userRepository.save(any())).thenReturn(newMockUser("test"));

        // when
        User user = userService.registerUser(createUserRequest.toEntity());

        // then
        assertThat(user.getEmail()).isEqualTo(createUserRequest.getEmail());
        assertThat(user.getName()).isEqualTo(createUserRequest.getName());
    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    void findAll() {
        // given
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("test@naver.com")
                .name("test")
                .password("1234")
                .build();

        // stub - list 반환
        List<User> userList = Arrays.asList(createUserRequest.toEntity());
        when(userRepository.findAll()).thenReturn(userList);

        // when
        List<User> users = userService.findAll();

        // then
        assertThat(users.get(0).getEmail()).isEqualTo(createUserRequest.getEmail());
        assertThat(users.get(0).getName()).isEqualTo(createUserRequest.getName());
        assertThat(users.size()).isEqualTo(1);
    }
}