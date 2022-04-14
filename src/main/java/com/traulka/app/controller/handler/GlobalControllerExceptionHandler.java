package com.traulka.app.controller.handler;

import com.traulka.app.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> responseException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Validation exception: %s", e.getMessage()));
    }
}
