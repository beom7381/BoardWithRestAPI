package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class DeleteException extends WebRequestException {
    public DeleteException() {
        super(RequestError.DELETE_FAILED);
    }
}
