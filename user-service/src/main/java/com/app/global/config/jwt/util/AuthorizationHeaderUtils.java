package com.app.global.config.jwt.util;

import com.app.global.config.jwt.constant.GrantType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {
    public static void validateAuthorization(String authorizationHeader) {

        // authorizationHeader 필수 값 체크
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }
        //authorizationHeader을 공백 단위로 입력 한 경우 2개의 배열로 나눠질것
        //배열의 길이가 2개인 경우 authorizationHeader 앞에 Bearer 값 체크
        String[] authorizations = authorizationHeader.split(" ");
        if (authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

    }

}
