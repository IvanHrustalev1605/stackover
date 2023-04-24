package com.javamentor.qa.platform.exception;

public class ApiNotFoundException extends RuntimeException {
    public ApiNotFoundException() {
    }

    public ApiNotFoundException(String message) {
        super(message);
    }
}
