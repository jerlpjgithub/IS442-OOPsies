package com.oopsies.server.payload.response;

import java.util.List;

/**
 * Payload for user info responses.
 * This class represents the payload of a user info response, containing the
 * user's id, email, and roles.
 */
public class UserInfoResponse {
    private Long id;
    private String email;
    private List<String> roles;

    /**
     * Constructs a new UserInfoResponse with the specified id, email, and roles.
     *
     * @param id    The id of the user.
     * @param email The email of the user.
     * @param roles The roles of the user.
     */
    public UserInfoResponse(Long id, String email, List<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Returns the id of the user.
     *
     * @return The id of the user.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the id of the user.
     *
     * @param id The new id of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Returns the roles of the user.
     *
     * @return The roles of the user.
     */
    public List<String> getRoles() {
        return this.roles;
    }

    /**
     * Sets the roles of the user.
     *
     * @param roles The new roles of the user.
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}