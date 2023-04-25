package com.javamentor.qa.platform.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthException extends AuthenticationException {
    public JwtAuthException(String detail) {
        super(detail);
    }

    public JwtAuthException(String detail, Throwable ex) {
        super(detail, ex);
    }
}

