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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}