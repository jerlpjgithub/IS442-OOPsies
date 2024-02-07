package com.oopsies.server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

/**
 * The User class represents an abstract class users within the system.
 * It includes details such as email, password, first and last names, roles, email verification status,
 * associated user activity log, and portfolios.
 *
 * The class is annotated with JPA annotations to define the table mapping, unique constraints,
 * and relationships with other entities.
 *
 * JsonIdentityInfo is used to handle circular references correctly when serializing entities to JSON.
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean emailVerified;

    /**
     * Default constructor required by Hibernate. Initializes a new user with email verification status set to true.
     */
    public User() {
        this.emailVerified = false;
    }

    /**
     * Parametrized constructor for creating a new User instance with specified attributes.
     * It initializes user activity log and portfolios as well.
     *
     * @param email     the email of the user, must be unique.
     * @param password  the password of the user.
     * @param firstName the first name of the user.
     * @param lastName  the last name of the user.
     * @param role      the role of the user within the system.
     */
    public User(String email, String password, String firstName, String lastName, String role, boolean emailVerified) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.emailVerified = emailVerified;
    }

    // --------------- Getters and Setters (start) ------------------
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEmailVerified() {
        return this.emailVerified;
    }

    public boolean getEmailVerified() {
        return this.emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    // --------------- Getters and Setters (end) ------------------

}
