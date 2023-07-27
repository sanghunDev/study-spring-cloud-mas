package com.app.api;

import com.app.api.com.Greeting;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {
    final private Greeting greeting;

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        log.info("server port={}", request.getServerPort());
        return "hello!! MAS";
    }

    @RequestMapping("/health_check")
    public String status() {
        return "UserService..ready";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }
}
