package com.app.domain.user.service;

import com.app.domain.user.entity.User;
import com.app.domain.user.repository.UserRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        validateDuplicateMember(user);
        return userRepository.save(user);
    }

    private void validateDuplicateMember(User user) {
        Optional<User> userOp = userRepository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
