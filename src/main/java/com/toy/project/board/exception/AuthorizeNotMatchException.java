package com.toy.project.board.exception;

public class AuthorizeNotMatchException extends RuntimeException {
    public AuthorizeNotMatchException(String message) {
        super(message);
    }
}
