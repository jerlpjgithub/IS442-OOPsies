package com.oopsies.server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * The User class represents an abstract class users within the system.
 * It includes details such as email, password, first and last names, roles,
 * email verification status,
 * associated user activity log, and portfolios.
 *
 * The class is annotated with JPA annotations to define the table mapping,
 * unique constraints,
 * and relationships with other entities.
 *
 * JsonIdentityInfo is used to handle circular references correctly when
 * serializing entities to JSON.
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Column()
    @Size(max = 50)
    private String firstName;

    @Column()
    @Size(max = 50)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private double accountBalance;

    @Enumerated(EnumType.STRING)
    private Provider provider;


    /**
     * Default constructor required by Hibernate. Initializes a new user with email
     * verification status set to true.
     */
    public User() {
        this.emailVerified = false;
    }

    /**
     * Constructor for email and password params for signup.
     */
    public User(String email, String password, String firstName, String lastName, double accountBalance) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
    }
    /**

    /**
     * Parametrized constructor for creating a new User instance with specified
     * attributes.
     * It initializes user activity log and portfolios as well.
     *
     * @param email     the email of the user, must be unique.
     * @param password  the password of the user.
     * @param firstName the first name of the user.
     * @param lastName  the last name of the user.
     * @param role      the role of the user within the system.
     */
    public User(String email, String password, String firstName, String lastName, Set<Role> role, boolean emailVerified,
            double accountBalance) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailVerified = emailVerified;
        this.accountBalance = accountBalance;
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // --------------- Getters and Setters (start) ------------------

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the new ID of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the roles of the user.
     *
     * @return the roles of the user.
     */
    public Set<Role> getRoles() {
        return this.roles;
    }

    /**
     * Sets the roles of the user.
     *
     * @param roles the new roles of the user.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Checks if the user's email is verified.
     *
     * @return true if the email is verified, false otherwise.
     */
    public boolean isEmailVerified() {
        return this.emailVerified;
    }

    /**
     * Returns the email verification status of the user.
     *
     * @return true if the email is verified, false otherwise.
     */
    public boolean getEmailVerified() {
        return this.emailVerified;
    }

    /**
     * Sets the email verification status of the user.
     *
     * @param emailVerified the new email verification status.
     */
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Returns the account balance of the user.
     *
     * @return the account balance of the user.
     */
    public double getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Sets the account balance of the user.
     *
     * @param accountBalance the new account balance of the user.
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Increments the account balance of the user by a specified amount.
     *
     * @param amount the amount to increment the account balance by.
     */
    public void incrementAccountBalance(double amount) {
        this.accountBalance += amount;
    }

    /**
     * Decrements the account balance of the user by a specified amount.
     *
     * @param amount the amount to decrement the account balance by.
     */
    public void decrementAccountBalance(double amount) {
        this.accountBalance -= amount;
    }

    /**
     * Returns the provider of the user.
     *
     * @return the provider of the user.
     */
    public Provider getProvider() {
        return this.provider;
    }

    /**
     * Sets the provider of the user.
     *
     * @param provider the new provider of the user.
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    // --------------- Getters and Setters (end) ------------------

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                ", emailVerified=" + emailVerified +
                ", accountBalance=" + accountBalance +
                ", provider=" + provider +
                '}';
    }
}
