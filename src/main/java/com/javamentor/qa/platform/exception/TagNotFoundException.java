package com.javamentor.qa.platform.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Тег с таким id не найден!");
    }

    public TagNotFoundException(String message) { super(message); }
}
