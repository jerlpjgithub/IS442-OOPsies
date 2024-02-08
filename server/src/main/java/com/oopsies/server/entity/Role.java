package com.oopsies.server.entity;

import jakarta.persistence.*;

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
     * Default constructor required by Hibernate. Initializes a new user with email verification status set to true.
     */
    public Role(){
    }

    /**
     * Parametrized constructor for creating a new Role instance with specified attributes.
     *
     * @param name      the name of the user within the system.
     */
    public Role(EnumRole name) {
        this.name = name;
    }

    // --------------- Getters and Setters (start) ------------------
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumRole getName() {
        return this.name;
    }

    public void setName(EnumRole name) {
        this.name = name;
    }
    // --------------- Getters and Setters (end) ------------------
}
