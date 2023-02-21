package com.javamentor.qa.platform.exception;

public class IgnoredTagAlreadyExistsException extends IllegalArgumentException {
    public IgnoredTagAlreadyExistsException() {
        super("Тег уже был добавлен в игнорируемые ранее!");
    }
}
