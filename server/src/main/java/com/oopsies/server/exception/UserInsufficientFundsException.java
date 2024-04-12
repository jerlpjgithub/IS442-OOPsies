package com.oopsies.server.exception;

/**
 * Exception for when user has insufficient funds
 */
public class UserInsufficientFundsException extends Exception {
    public UserInsufficientFundsException() {
        super("User does not have enough funds in his account!");
    }

    public UserInsufficientFundsException(String message) {
        super(message);
    }

    public UserInsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
