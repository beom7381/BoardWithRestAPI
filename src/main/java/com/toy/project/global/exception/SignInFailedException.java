package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class SignInFailedException extends WebRequestException {
    public SignInFailedException() {
        super(RequestError.LOGIN_FAILED);
    }
}
