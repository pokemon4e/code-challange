package com.social.app.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom ExceptionHandler for the REST API of the social app.
 */
@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleConflict(
            final RuntimeException ex, final WebRequest request) {

        ApiException apiException = new ApiException(
                HttpStatus.BAD_REQUEST, ex, "Argument exception");
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
