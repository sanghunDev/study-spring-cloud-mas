package com.app.global.config.security;

import com.MySpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@MySpringBootTest
class WebSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("인증이 되지 않은 경우")
    void 인증_실패() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/user-service/welcome"));
        String resBody = resultActions.andReturn().getResponse().getContentAsString();
        int resStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("resBody = " + resBody);
        System.out.println("resStatusCode = " + resStatusCode);

        // then
        assertThat(resStatusCode).isEqualTo(403);
    }

    @Test
    @DisplayName("인증이 필요없는 URL")
    void 인증_불필요() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/users"));
        String resBody = resultActions.andReturn().getResponse().getContentAsString();
        int resStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("resBody = " + resBody);
        System.out.println("resStatusCode = " + resStatusCode);

        // then
        assertThat(resStatusCode).isEqualTo(200);
    }

}