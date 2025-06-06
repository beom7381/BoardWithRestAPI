package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class AccessTokenExpiredException extends WebRequestException {
    public AccessTokenExpiredException() {
        super(RequestError.ACCESSTOKEN_EXPIRED);
    }
}
