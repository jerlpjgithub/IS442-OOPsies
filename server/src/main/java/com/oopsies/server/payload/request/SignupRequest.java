package com.oopsies.server.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Payload for signup requests.
 * This class represents the payload of a signup request, containing the user's email, password, roles, and name.
 */
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Size(max=20)
    private String firstName;

    @Size(max=20)
    private String lastName;

    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
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
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the roles of the user.
     *
     * @return The roles of the user.
     */
    public Set<String> getRole() {
        return this.role;
    }

    /**
     * Sets the roles of the user.
     *
     * @param role The new roles of the user.
     */
    public void setRole(Set<String> role) {
        this.role = role;
    }

    /**
     * Returns the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The new first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The new last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}