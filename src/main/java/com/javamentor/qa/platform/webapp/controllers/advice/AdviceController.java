package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.exception.ConstrainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<String> handleApiRequestException(ApiRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstrainException.class)
    public ResponseEntity<String> handleConstrainException(ConstrainException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
