package com.oopsies.server.entity;

/**
 * Entity representing a Google user.
 * This is used to store and manipulate data of a user authenticated via Google.
 */
public class GoogleUser {
    private String id;
    private String email;
    private String name;
    private String picture;

    /**
     * Default constructor.
     */
    public GoogleUser() {}

    /**
     * Full constructor.
     *
     * @param id The user's Google ID.
     * @param email The user's email.
     * @param name The user's name.
     * @param picture The URL of the user's profile picture.
     */
    public GoogleUser(String id, String email, String name, String picture) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    /**
     * Gets the user's Google ID.
     *
     * @return The user's Google ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's Google ID.
     *
     * @param id The user's Google ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email The user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name The user's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the URL of the user's profile picture.
     *
     * @return The URL of the user's profile picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the URL of the user's profile picture.
     *
     * @param picture The URL of the user's profile picture.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
