package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;


/**
 * The Booking class represents an abstract class users within the system.
 * <p>
 * The class is annotated with JPA annotations to define the table mapping,
 * unique constraints,
 * and relationships with other entities.
 * <p>
 * JsonIdentityInfo is used to handle circular references correctly when
 * serializing entities to JSON.
 */
@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name = "bookingDate", nullable = false)
    private Date bookingDate;

    @Column(name = "cancelDate")
    private Date cancelDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    /**
     * Returns the booking ID.
     *
     * @return A long representing the booking ID.
     */
    public long getBookingID() {
        return id;
    }

    /**
     * Sets the booking ID.
     *
     * @param id The booking ID to set.
     */
    public void setBookingID(long id) {
        this.id = id;
    }

    /**
     * Returns the User associated with the booking.
     *
     * @return A User object representing the user.
     */
    public User getUser(){
        return this.user;
    }

    /**
     * Sets the User associated with the booking.
     *
     * @param user The User to set.
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Returns the Event associated with the booking.
     *
     * @return An Event object representing the event.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the Event associated with the booking.
     *
     * @param event The Event to set.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns the booking date.
     *
     * @return A Date object representing the booking date.
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date.
     *
     * @param bookingDate The booking date to set.
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Returns the cancellation date.
     *
     * @return A Date object representing the cancellation date.
     */
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * Sets the cancellation date.
     *
     * @param cancelDate The cancellation date to set.
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * Returns a string representation of the Booking object.
     *
     * @return A string representation of the Booking object.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", eventID=" + event +
                ", bookingDate=" + bookingDate +
                ", cancelDate=" + cancelDate +
                ", user=" + user +
                '}';
    }
}