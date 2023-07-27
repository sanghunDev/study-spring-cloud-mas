package com.app.api.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLogin {

    @NotNull(message = "이메일을 입력 해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해야 합니다")
    private String email;

    @NotEmpty(message = "암호를 입력 해주세요")
    @Size(min = 4, max = 20)
    private String password;
}
