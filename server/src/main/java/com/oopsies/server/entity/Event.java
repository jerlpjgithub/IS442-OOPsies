package com.oopsies.server.entity;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Event class represents an event entity in the system.
 * It contains details about the event such as its name, manager, date and time, venue, cancellation date, capacity, cancellation fee, and ticket price.
 */
@Entity
@Table (name = "events")
//@IdClass(EventId.class)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String eventName;
    @ManyToOne
    @JoinColumn(name="manager_id")
    private User manager;
    @Column(nullable = false)
    private Date dateTime;
    @Column(nullable = false)
    private String venue;
    private Date cancelDate;
    @Column(nullable = false)
    private int capacity;
    @Column(columnDefinition = "double default 0.0")
    private double cancellationFee;
    @Column(nullable = false)
    private double ticketPrice;

    public Event() { }

/**
 * Constructs an Event object with the given parameters.
 *
 * @param eventName The name of the event.
 * @param manager The manager of the event.
 * @param dateTime The date and time of the event.
 * @param venue The venue of the event.
 * @param cancelDate The cancellation date of the event.
 * @param capacity The capacity of the event.
 * @param cancellationFee The cancellation fee of the event.
 * @param ticketPrice The ticket price of the event.
 */
public Event(String eventName, User manager, Date dateTime, String venue, Date cancelDate, int capacity, double cancellationFee, double ticketPrice) {
    this.eventName = eventName;
    this.manager = manager;
    this.dateTime = dateTime;
    this.venue = venue;
    this.cancelDate = cancelDate;
    this.capacity = capacity;
    this.cancellationFee = cancellationFee;
    this.ticketPrice = ticketPrice;
}

/**
 * Updates the details of the Event object with the given parameters.
 *
 * @param eventName The new name of the event.
 * @param manager The new manager of the event.
 * @param dateTime The new date and time of the event.
 * @param venue The new venue of the event.
 * @param cancelDate The new cancellation date of the event.
 * @param capacity The new capacity of the event.
 * @param cancellationFee The new cancellation fee of the event.
 * @param ticketPrice The new ticket price of the event.
 */
public void updateDetails(String eventName, User manager, Date dateTime, String venue, Date cancelDate, int capacity, double cancellationFee, double ticketPrice) {
    this.eventName = eventName;
    this.manager = manager;
    this.dateTime = dateTime;
    this.venue = venue;
    this.cancelDate = cancelDate;
    this.capacity = capacity;
    this.cancellationFee = cancellationFee;
    this.ticketPrice = ticketPrice;
}

// getters and setters

/**
 * Returns the ID of the Event.
 *
 * @return A Long representing the ID of the Event.
 */
public Long getId() {
    return this.id;
}

/**
 * Sets the ID of the Event.
 *
 * @param id The ID to set.
 */
public void setId(Long id) {
    this.id = id;
}

/**
 * Returns the name of the Event.
 *
 * @return A string representing the name of the Event.
 */
public String getEventName() {
    return this.eventName;
}

/**
 * Sets the name of the Event.
 *
 * @param eventName The name to set.
 */
public void setEventName(String eventName) {
    this.eventName = eventName;
}

/**
 * Sets the manager of the Event.
 *
 * @param manager The User to set as manager.
 */
public void setManagerID(User manager){
    this.manager = manager;
}

/**
 * Returns the manager of the Event.
 *
 * @return A User object representing the manager of the Event.
 */
public User getManagerID(){
    return this.manager;
}

/**
 * Returns the date and time of the Event.
 *
 * @return A Date object representing the date and time of the Event.
 */
public Date getDateTime() {
    return this.dateTime;
}

/**
 * Sets the date and time of the Event.
 *
 * @param dateTime The Date to set.
 */
public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
}

/**
 * Returns the venue of the Event.
 *
 * @return A string representing the venue of the Event.
 */
public String getVenue() {
    return this.venue;
}

/**
 * Sets the venue of the Event.
 *
 * @param venue The venue to set.
 */
public void setVenue(String venue) {
    this.venue = venue;
}

/**
 * Checks if the Event is cancelled.
 *
 * @return true if the Event is cancelled; otherwise, false.
 */
public boolean isEventCancelled() {
    return this.cancelDate != null;
}

/**
 * Returns the cancellation date of the Event.
 *
 * @return A Date object representing the cancellation date of the Event.
 */
public Date getEventCancelDate() {
    return this.cancelDate;
}

/**
 * Sets the cancellation date of the Event.
 *
 * @param cancelDate The cancellation date to set.
 */
public void setEventCancelled(Date cancelDate) {
    this.cancelDate = cancelDate;
}

/**
 * Returns the capacity of the Event.
 *
 * @return An integer representing the capacity of the Event.
 */
public int getCapacity() {
    return this.capacity;
}

/**
 * Sets the capacity of the Event.
 *
 * @param capacity The capacity to set.
 */
public void setCapacity(int capacity) {
    this.capacity = capacity;
}

/**
 * Returns the cancellation fee of the Event.
 *
 * @return A double representing the cancellation fee of the Event.
 */
public double getCancellationFee() {
    return this.cancellationFee;
}

/**
 * Sets the cancellation fee of the Event.
 *
 * @param cancellationFee The cancellation fee to set.
 */
public void setCancellationFee(double cancellationFee) {
    this.cancellationFee = cancellationFee;
}

/**
 * Returns the ticket price of the Event.
 *
 * @return A double representing the ticket price of the Event.
 */
public double getTicketPrice() {
    return this.ticketPrice;
}

/**
 * Sets the ticket price of the Event.
 *
 * @param ticketPrice The ticket price to set.
 */
public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
}

/**
 * Returns a string representation of the Event object.
 *
 * @return A string representation of the Event object.
 */
@Override
public String toString() {
    return "Event{" +
            "id=" + id +
            ", eventName='" + eventName + '\'' +
            ", manager=" + manager +
            ", dateTime=" + dateTime +
            ", venue='" + venue + '\'' +
            ", cancelDate=" + cancelDate +
            ", capacity=" + capacity +
            ", cancellationFee=" + cancellationFee +
            ", ticketPrice=" + ticketPrice +
            '}';
}}