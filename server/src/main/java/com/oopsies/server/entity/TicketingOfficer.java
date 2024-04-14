package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Set;

/**
 * The TicketingOfficer class represents an entity model for a TicketingOfficer a subclass of User.
 * It includes details such as email, password, first and last names, roles, email verification status,
 * and other methods unique to theticket officer.
 *
 * The class is annotated with JPA annotations to define the table mapping, unique constraints,
 * and relationships with other entities.
 *
 * JsonIdentityInfo is used to handle circular references correctly when serializing entities to JSON.
 */
@Entity
@Table(name = "ticketingofficers")
public class TicketingOfficer extends User {
    
    /**
     * Default constructor required by Hibernate.
     */
    public TicketingOfficer() {}

    /**
     * Parametrized constructor for creating a new TicketingOfficer instance with specified attributes.
     *
     * @param email     the email of the user, must be unique.
     * @param password  the password of the user.
     * @param firstName the first name of the user.
     * @param lastName  the last name of the user.
     */
    public TicketingOfficer(String email, String password, String firstName, String lastName, Set<Role> role, boolean emailVerified, double accountBalance) {
        super(email, password, firstName, lastName, role, emailVerified, accountBalance);
    }
}
