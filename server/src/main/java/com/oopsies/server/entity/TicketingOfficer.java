package com.oopsies.server.entity;

import jakarta.persistence.*;

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
    public TicketingOfficer(String email, String password, String firstName, String lastName, String role, boolean emailVerified) {
        super(email, password, firstName, lastName, "ticketingofficer", emailVerified);
    }
}
