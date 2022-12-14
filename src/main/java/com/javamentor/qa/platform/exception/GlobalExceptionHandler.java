package com.javamentor.qa.platform.exception;

import com.javamentor.qa.platform.models.dto.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ApiRequestException.class,
    ConstrainException.class,
    UserNotFoundException.class})
    public ResponseEntity<AppError> handleException(Exception e) {
        HttpStatus status;
        if (e instanceof ApiRequestException) {
            status = HttpStatus.BAD_REQUEST;

        } else if (e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new AppError(status.value(), e.getMessage()), status);
    }
}
