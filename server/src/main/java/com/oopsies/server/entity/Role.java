package com.oopsies.server.entity;

import jakarta.persistence.*;

/**
 * Entity representing a role in the system.
 * This is used to manage access control in the application.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole name;

    /**
     * Default constructor required by Hibernate.
     */
    public Role() {
    }

    /**
     * Parametrized constructor for creating a new Role instance with specified
     * attributes.
     * 
     * @param name the name of the role within the system.
     */
    public Role(EnumRole name) {
        this.name = name;
    }

    // --------------- Getters and Setters (start) ------------------

    /**
     * Gets the role's ID.
     *
     * @return The role's ID.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the role's ID.
     *
     * @param id The role's ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the role's name.
     *
     * @return The role's name.
     */
    public EnumRole getName() {
        return this.name;
    }

    /**
     * Sets the role's name.
     *
     * @param name The role's name.
     */
    public void setName(EnumRole name) {
        this.name = name;
    }
    // --------------- Getters and Setters (end) ------------------
}