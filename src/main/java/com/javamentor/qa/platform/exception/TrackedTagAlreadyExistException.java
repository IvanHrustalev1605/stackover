package com.javamentor.qa.platform.exception;

public class TrackedTagAlreadyExistException  extends RuntimeException{

    public TrackedTagAlreadyExistException() {
    }

    public TrackedTagAlreadyExistException(String message) {
        super(message);
    }
}
