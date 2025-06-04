package com.toy.project.global.exception;

import com.toy.project.global.enums.RequestError;
import lombok.Getter;

@Getter
public abstract class WebRequestException extends RuntimeException {

    private final RequestError error;

    public WebRequestException(RequestError error){
        super(error.getMessage());
        this.error = error;
    }
}
