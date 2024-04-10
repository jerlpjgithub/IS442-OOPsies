package com.oopsies.server.advice;

import java.util.Date;

/**
 * Class representing an error message.
 * This is used to standardize the format of error messages returned by the API.
 */
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    /**
     * Full constructor.
     *
     * @param statusCode The HTTP status code of the error.
     * @param timestamp The time the error occurred.
     * @param message A short description of the error.
     * @param description A detailed description of the error.
     */
    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    /**
     * Gets the HTTP status code of the error.
     *
     * @return The HTTP status code of the error.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the time the error occurred.
     *
     * @return The time the error occurred.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets a short description of the error.
     *
     * @return A short description of the error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets a detailed description of the error.
     *
     * @return A detailed description of the error.
     */
    public String getDescription() {
        return description;
    }
}