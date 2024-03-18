package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setManagerID(User manager){
        this.manager = manager;
    }

    public User getManagerID(){
        return this.manager;
    }
    
    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public boolean isEventCancelled() {
        return this.cancelDate != null;
    }

    public Date getEventCancelDate() {
        return this.cancelDate;
    }

    public void setEventCancelled(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCancellationFee() {
        return this.cancellationFee;
    }

    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public double getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

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
    }
}