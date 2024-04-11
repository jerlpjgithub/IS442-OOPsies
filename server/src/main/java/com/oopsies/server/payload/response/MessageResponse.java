package com.oopsies.server.payload.response;

/**
 * Payload for HTTP responses
 * Contains HTTP status code, message and
 * relevant data to the request
 */
public class MessageResponse<T> {
    private int status;
    private String message;
    private T data;

    public MessageResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
