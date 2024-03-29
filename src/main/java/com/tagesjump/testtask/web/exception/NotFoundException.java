package com.tagesjump.testtask.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    private final String message;

    public NotFoundException(HttpStatus status, String message) {
        super(status, message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
