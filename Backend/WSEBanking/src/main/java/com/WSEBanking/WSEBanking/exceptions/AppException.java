package com.WSEBanking.WSEBanking.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for application-specific errors.
 */
public class AppException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Constructs a new AppException with the specified error message and HTTP status.
     *
     * @param message The error message.
     * @param status  The HTTP status code associated with the exception.
     */
    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Gets the HTTP status code associated with the exception.
     *
     * @return The HTTP status code.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
