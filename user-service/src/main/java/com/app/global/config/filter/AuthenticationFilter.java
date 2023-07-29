package com.app.global.config.filter;

import com.app.api.com.service.RedisService;
import com.app.api.user.dto.RequestLogin;
import com.app.api.user.dto.UserResponse;
import com.app.api.user.service.UserInfoService;
import com.app.domain.user.constant.Role;
import com.app.global.config.jwt.dto.JwtTokenDto;
import com.app.global.config.jwt.service.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserInfoService userInfoService;
    private final TokenManager tokenManager;
    private final RedisService redisService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin requestLoginInfo = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLoginInfo.getEmail(),
                            requestLoginInfo.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userEmail = ((User) authResult.getPrincipal()).getUsername();
        UserResponse userInfo = userInfoService.findByEmail(userEmail);
        String userId = userInfo.getUserId();
        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(userId, Role.USER);

        redisService.setValues(userId, jwtTokenDto);
        response.addHeader("userId", userId);
        response.addHeader("accessToken", jwtTokenDto.getAccessToken());
        response.addHeader("refreshToken", jwtTokenDto.getRefreshToken());

        //super.successfulAuthentication(request, response, chain, authResult);
    }

}
