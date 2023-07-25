package com.app.global.error.exception;

import com.app.global.error.ErrorCode;
import lombok.Getter;

/**
 * 비지니스 로직 수행중 발생하는 에러 처리
 */
@Getter
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
