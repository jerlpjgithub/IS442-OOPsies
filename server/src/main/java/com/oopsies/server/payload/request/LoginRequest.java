package com.oopsies.server.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Payload for login requests.
 * This class represents the payload of a login request, containing the user's email and password.
 */
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}