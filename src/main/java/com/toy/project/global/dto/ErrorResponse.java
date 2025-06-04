package com.toy.project.global.dto;

import com.toy.project.global.enums.RequestError;
import lombok.*;

@Getter
@ToString
public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(RequestError error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
