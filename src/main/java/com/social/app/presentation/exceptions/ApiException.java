package com.social.app.presentation.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Class that holds relevant information about exceptions that happen during
 * REST calls.
 */
final class ApiException {

    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime dateTime;
    private HttpStatus status;
    private String message;
    private String debugMessage;

    private ApiException() {
        dateTime = LocalDateTime.now();
    }

    ApiException(final HttpStatus status) {
        this();
        this.status = status;
    }

    ApiException(final HttpStatus status, final Throwable ex) {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiException(
            final HttpStatus status,
            final Throwable ex,
            final String message) {
        this(status, ex);
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }
}
