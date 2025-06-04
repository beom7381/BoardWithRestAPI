package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class AuthorNotMatchException extends WebRequestException {
    public AuthorNotMatchException() {
        super(RequestError.INVALID_AUTHORIZE);
    }
}
