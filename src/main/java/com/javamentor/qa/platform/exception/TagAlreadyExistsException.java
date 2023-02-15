package com.javamentor.qa.platform.exception;

public class TagAlreadyExistsException extends IllegalArgumentException {
    public TagAlreadyExistsException() {
        super("Тег уже был добавлен в отслеживаемые ранее!");
    }
}
