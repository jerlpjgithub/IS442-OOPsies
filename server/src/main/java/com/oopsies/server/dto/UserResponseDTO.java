package com.oopsies.server.dto;

import java.util.Set;

import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.Provider;

/**
 * Data Transfer Object for User responses.
 * This is used to send user data to the client.
 */
public class UserResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private boolean emailVerified;
    private double accountBalance;
    private Provider provider;

    /**
     * Default constructor.
     */
    public UserResponseDTO() {
    }

    /**
     * Full constructor.
     *
     * @param id             The user ID.
     * @param email          The user email.
     * @param firstName      The user's first name.
     * @param lastName       The user's last name.
     * @param roles          The user's roles.
     * @param emailVerified  Whether the user's email is verified.
     * @param accountBalance The user's account balance.
     * @param provider       The user's provider.
     */
    public UserResponseDTO(Long id, String email, String firstName, String lastName, Set<Role> roles,
            boolean emailVerified, double accountBalance, Provider provider) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.emailVerified = emailVerified;
        this.accountBalance = accountBalance;
        this.provider = provider;
    }

    /**
     * Returns the user ID.
     *
     * @return The user ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id The user ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user email.
     *
     * @return The user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user email.
     *
     * @param email The user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName The user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName The user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the user's roles.
     *
     * @return The user's roles.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the user's roles.
     *
     * @param roles The user's roles.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns whether the user's email is verified.
     *
     * @return Whether the user's email is verified.
     */
    public boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Sets whether the user's email is verified.
     *
     * @param emailVerified Whether the user's email is verified.
     */
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Returns the user's account balance.
     *
     * @return The user's account balance.
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the user's account balance.
     *
     * @param accountBalance The user's account balance.
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Returns the user's provider.
     *
     * @return The user's provider.
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the user's provider.
     *
     * @param provider The user's provider.
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}