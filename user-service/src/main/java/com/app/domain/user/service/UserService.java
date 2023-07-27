package com.app.domain.user.service;

import com.app.domain.user.entity.User;
import com.app.domain.user.repository.UserRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.global.error.ErrorCode.MEMBER_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new AuthenticationException(MEMBER_NOT_EXISTS));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
