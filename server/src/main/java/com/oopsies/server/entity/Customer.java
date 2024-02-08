package com.oopsies.server.entity;

import jakarta.persistence.*;

/**
 * The User class represents an entity model for a customer a subclass of User.
 * It includes details such as the account balance and booking history.
 *
 * The class is annotated with JPA annotations to define the table mapping, unique constraints,
 * and relationships with other entities.
 *
 * JsonIdentityInfo is used to handle circular references correctly when serializing entities to JSON.
 */
@Entity
@Table(name = "customers")
public class Customer extends User {

    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Booking> bookingHistory; 

    /**
     * Default constructor required by Hibernate. Initializes a new Customer with role set to Customer.
     */
    public Customer() {
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
     * @param accountBalance the balance of that user.
     */
    public Customer(String email, String password, String firstName, String lastName, boolean emailVerified, double accountBalance) {
        super(email, password, firstName, lastName, "Customer", emailVerified, accountBalance);
    }

    // --------------- Getters and Setters (start) ------------------

    // --------------- Getters and Setters (end) ------------------

}
