package com.oopsies.server.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.oopsies.server.exception.TokenRefreshException;

/**
 * Controller advice for handling exceptions related to tokens.
 * This class centralizes exception handling for all controllers that deal with tokens.
 */
@RestControllerAdvice
public class TokenControllerAdvice {

    /**
     * Handles TokenRefreshException.
     *
     * @param ex The exception that was thrown.
     * @param request The current web request.
     * @return An error message containing details about the exception.
     */
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}