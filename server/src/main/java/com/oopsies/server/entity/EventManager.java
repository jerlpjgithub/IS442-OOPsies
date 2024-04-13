package com.oopsies.server.entity;

import java.util.Set;

import com.oopsies.server.services.TicketService;
import jakarta.persistence.*;

/**
 * The EventManager class represents an entity model for a EventManager a subclass of User.
 * It includes details such as email, password, first and last names, roles, email verification status,
 * and other methods unique to the event manager.
 *
 * The class is annotated with JPA annotations to define the table mapping, unique constraints,
 * and relationships with other entities.
 *
 * JsonIdentityInfo is used to handle circular references correctly when serializing entities to JSON.
 */
@Entity
@Table(name = "eventmanagers")
public class EventManager extends User {

    /**
     * Default constructor required by Hibernate.
     */
    public EventManager() { }

    /**
     * Parametrized constructor for creating a new EventManager instance with specified attributes.
     * It initializes user activity log and portfolios as well.
     *
     * @param email     the email of the user, must be unique.
     * @param password  the password of the user.
     * @param firstName the first name of the user.
     * @param lastName  the last name of the user.
     */
    public EventManager(String email, String password, String firstName, String lastName, Set<Role> role, boolean emailVerified, double accountBalance, TicketService ticketService) {
        super(email, password, firstName, lastName, role, emailVerified, accountBalance);
    }
}
