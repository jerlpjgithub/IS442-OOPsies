package com.oopsies.server.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Entity class representing a refresh token.
 */
@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    /**
     * Returns the ID of this refresh token.
     *
     * @return the ID of this refresh token
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the ID of this refresh token.
     *
     * @param id the new ID of this refresh token
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user associated with this refresh token.
     *
     * @return the user associated with this refresh token
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user associated with this refresh token.
     *
     * @param user the new user associated with this refresh token
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the token of this refresh token.
     *
     * @return the token of this refresh token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Sets the token of this refresh token.
     *
     * @param token the new token of this refresh token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the expiry date of this refresh token.
     *
     * @return the expiry date of this refresh token
     */
    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Sets the expiry date of this refresh token.
     *
     * @param expiryDate the new expiry date of this refresh token
     */
    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
    
}
