package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class RefreshTokenExpiredException extends WebRequestException {
    public RefreshTokenExpiredException() {
        super(RequestError.REFRESHTOKEN_EXPIRED);
    }
}
