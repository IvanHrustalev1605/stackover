package com.javamentor.qa.platform.exception;

import com.javamentor.qa.platform.models.dto.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler({ApiRequestException.class,
            ConstrainException.class,Exception.class})
    public ResponseEntity<AppError> handleException(Exception exception) {

        HttpStatus status;

        if (exception instanceof ApiRequestException || exception instanceof ConstrainException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new AppError(status.value(), exception.getMessage()), status);
    }



}
