package com.tagesjump.testtask.web;

import com.tagesjump.testtask.web.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", getDefaultMessage(ex));
        log.warn("{} at request  {}: {}", status.getReasonPhrase(), request.getDescription(false), getDefaultMessage(ex));
        return ResponseEntity.status(status).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        log.warn("{} at request  {}: {}", status.getReasonPhrase(), request.getDescription(false), ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> appException(NotFoundException ex, WebRequest request) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        HttpStatus status = ex.getStatus();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        log.warn("{} at request  {}: {}", status.getReasonPhrase(), request.getDescription(false), ex.toString());
        return ResponseEntity.status(status).body(body);
    }

    private String getDefaultMessage(BindException ex) {
        StringBuilder message = new StringBuilder();
        ex.getAllErrors().forEach(error -> message.append(error.getDefaultMessage()).append(System.lineSeparator()));
        return message.toString();
    }
}