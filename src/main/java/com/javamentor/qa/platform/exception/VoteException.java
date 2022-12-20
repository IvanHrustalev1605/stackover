package com.javamentor.qa.platform.exception;

public class VoteException extends RuntimeException {

    private String message;

    public VoteException(String message) {

        this.message = message;
    }
}
