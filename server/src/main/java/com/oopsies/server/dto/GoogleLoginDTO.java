package com.oopsies.server.dto;

/**
 * Data Transfer Object for Google login.
 * This is used to receive and manipulate data related to a user logging in via
 * Google.
 */
public class GoogleLoginDTO {
    private String credential;
    private String clientId;

    /**
     * Default constructor.
     */
    public GoogleLoginDTO() {
    }

    /**
     * Gets the user's Google credential.
     *
     * @return The user's Google credential.
     */
    public String getCredential() {
        return credential;
    }

    /**
     * Sets the user's Google credential.
     *
     * @param credential The user's Google credential.
     */
    public void setCredential(String credential) {
        this.credential = credential;
    }

    /**
     * Gets the client ID used for Google login.
     *
     * @return The client ID used for Google login.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID used for Google login.
     *
     * @param clientId The client ID used for Google login.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}