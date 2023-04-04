package com.javamentor.qa.platform.exception;

public class PagePaginationException extends RuntimeException{
    public PagePaginationException() {
    }

    public PagePaginationException(String message) {
        super(message);
    }
}
