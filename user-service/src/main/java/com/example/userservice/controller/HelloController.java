package com.example.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class HelloController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        log.info("server port={}", request.getServerPort());
        return "hello!! MAS";
    }
}
