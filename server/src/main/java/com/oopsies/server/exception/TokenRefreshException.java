package com.oopsies.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a token refresh operation has failed.
 * This class is a custom exception used to indicate that a token refresh operation has failed.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new TokenRefreshException with the specified token and detail message.
     *
     * @param token The token that could not be refreshed.
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s] : %s", token, message));
    }
}