package com.toy.project.authorize.exception;

public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String message) {
        super(message);
    }
}
