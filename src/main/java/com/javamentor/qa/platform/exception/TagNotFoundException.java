package com.javamentor.qa.platform.exception;

import javassist.NotFoundException;

public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException() {
        super("Тег с таким id не найден!");
    }
}
