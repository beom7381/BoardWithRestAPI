package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class UserNotFoundException extends WebRequestException {
    public UserNotFoundException() {
        super(RequestError.INVALID_AUTHORIZE);
    }
}
