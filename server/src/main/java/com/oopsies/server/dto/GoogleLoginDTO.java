package com.oopsies.server.dto;

public class GoogleLoginDTO{
    private String credential;
    private String clientId;

    // Constructors, Getters, and Setters
    public GoogleLoginDTO() {}

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
