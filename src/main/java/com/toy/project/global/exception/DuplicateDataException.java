package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;

public class DuplicateDataException extends WebRequestException {
    public DuplicateDataException() {
        super(RequestError.DUPLICATE_DATA);
    }
}
