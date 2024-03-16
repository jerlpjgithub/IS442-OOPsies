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

    private String eventName;
    private long managerID;
//    @Id
    private Date dateTime;
//    @Id
    private String venue;
    @Column(columnDefinition = "boolean default false")
    private boolean eventCancelled;
    @Column(nullable = false)
    private int capacity;
    @Column(columnDefinition = "double default 0.0")
    private double cancellationFee;
    @Column(nullable = false)
    private double ticketPrice;

    public Event() { }

    public Event(String eventName, long managerID, Date dateTime, String venue, boolean eventCancelled, int capacity, double cancellationFee, double ticketPrice) {
        this.eventName = eventName;
        this.managerID = managerID;
        this.dateTime = dateTime;
        this.venue = venue;
        this.eventCancelled = eventCancelled;
        this.capacity = capacity;
        this.cancellationFee = cancellationFee;
        this.ticketPrice = ticketPrice;
    }

    public void updateDetails(String eventName, Date dateTime, String venue, boolean eventCancelled, int capacity, double cancellationFee, double ticketPrice) {
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.venue = venue;
        this.eventCancelled = eventCancelled;
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

    public void setManagerID(long managerID){
        this.managerID = managerID;
    }

    public long getManagerID(){
        return this.managerID;
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
        return this.eventCancelled;
    }

    public boolean getEventCancelled() {
        return this.eventCancelled;
    }

    public void setEventCancelled(boolean eventCancelled) {
        this.eventCancelled = eventCancelled;
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
                ", managerID=" + managerID +
                ", dateTime=" + dateTime +
                ", venue='" + venue + '\'' +
                ", eventCancelled=" + eventCancelled +
                ", capacity=" + capacity +
                ", cancellationFee=" + cancellationFee +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}