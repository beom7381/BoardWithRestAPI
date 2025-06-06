package com.toy.project.global.handler;

import com.toy.project.global.dto.ErrorResponse;
import com.toy.project.global.enums.RequestError;
import com.toy.project.global.exception.WebRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(WebRequestException.class)
    public ResponseEntity<ErrorResponse> handleWebRequestException(WebRequestException exception){
        var error = exception.getError();

        exception.printStackTrace();;

        return ResponseEntity.status(error.getStatus())
                .body(new ErrorResponse(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        exception.printStackTrace();

        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(RequestError.INTERNAL_SERVER_ERROR));
    }
}