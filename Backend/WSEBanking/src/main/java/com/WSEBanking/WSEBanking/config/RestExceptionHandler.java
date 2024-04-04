package com.WSEBanking.WSEBanking.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WSEBanking.WSEBanking.api.DTOs.ErrorDto;
import com.WSEBanking.WSEBanking.exceptions.AppException;

/**
 * Global exception handler for REST controllers.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles exceptions of type AppException.
     *
     * @param ex The AppException instance.
     * @return ResponseEntity containing an ErrorDto with appropriate status code and message.
     */
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
}