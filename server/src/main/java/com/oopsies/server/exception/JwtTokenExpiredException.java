package com.oopsies.server.exception;

/**
 * Exception indicating that a JWT token has expired.
 * This class is a custom exception used to indicate that a JWT token has expired.
 */
public class JwtTokenExpiredException extends RuntimeException {

    /**
     * Constructs a new JwtTokenExpiredException with the specified detail message.
     *
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}