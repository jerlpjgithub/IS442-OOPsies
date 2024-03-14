package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eventName;
    private long managerID;
    private Date dateTime;
    private String venue;
    private boolean eventCancelled;
    private int capacity;
    private double cancellationFee;
    private double ticketPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Event() { }

    public Event(Long id, String eventName, long managerID, Date dateTime, String venue, boolean eventCancelled, int capacity, double cancellationFee, double ticketPrice, List<Ticket> tickets) {
        this.id = id;
        this.eventName = eventName;
        this.managerID = managerID;
        this.dateTime = dateTime;
        this.venue = venue;
        this.eventCancelled = eventCancelled;
        this.capacity = capacity;
        this.cancellationFee = cancellationFee;
        this.ticketPrice = ticketPrice;
        this.tickets = tickets;
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

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTicketOptions(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}