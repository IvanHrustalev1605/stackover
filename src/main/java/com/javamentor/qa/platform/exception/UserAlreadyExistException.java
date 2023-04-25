package com.javamentor.qa.platform.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
    }
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
