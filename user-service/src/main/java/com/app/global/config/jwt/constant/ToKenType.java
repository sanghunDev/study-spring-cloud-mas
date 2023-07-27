package com.app.global.config.jwt.constant;

public enum ToKenType {

    ACCESS, REFRESH;

    public static boolean isAccessToken(String tokenType) {
        return ToKenType.ACCESS.name().equals(tokenType);
    }
}
