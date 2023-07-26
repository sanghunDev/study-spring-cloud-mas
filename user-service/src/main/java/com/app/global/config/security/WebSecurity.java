package com.app.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private static final String[] WHITE_LIST = {
            "/users/**",
            "/login",
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity security) throws Exception {
        // rest api 사용시 불필요한 설정 disable
        security.csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable();

        return security.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                        .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()
                        .anyRequest().authenticated()

        ).build();
    }
}
