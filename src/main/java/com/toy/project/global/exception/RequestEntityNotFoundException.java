package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class RequestEntityNotFoundException extends WebRequestException {
    public RequestEntityNotFoundException() {
        super(RequestError.ENTITY_NOT_FOUND);
    }
}
