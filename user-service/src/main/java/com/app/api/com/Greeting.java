package com.app.api.com;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Greeting {

    @Value("${greeting.message}")
    private String message;
}
