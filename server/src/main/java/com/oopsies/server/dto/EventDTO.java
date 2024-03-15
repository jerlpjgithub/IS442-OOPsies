package com.oopsies.server.dto;

import java.util.Date;

public class EventDTO {
    private Long id;
    private String eventName;
    private Date dateTime;
    private String venue;
    private boolean eventCancelled;
    private int capacity;
    private double cancellationFee;
    private double ticketPrice;

    public EventDTO() { }

    public EventDTO(Long id, String eventName, Date dateTime, String venue, boolean eventCancelled, int capacity, double cancellationFee, double ticketPrice) {
        this.id = id;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.venue = venue;
        this.eventCancelled = eventCancelled;
        this.capacity = capacity;
        this.cancellationFee = cancellationFee;
        this.ticketPrice = ticketPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public boolean isEventCancelled() {
        return eventCancelled;
    }

    public void setEventCancelled(boolean eventCancelled) {
        this.eventCancelled = eventCancelled;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public EventDTO eventID(long eventId) {
        setId(eventId);
        return this;
    }

    public EventDTO eventName(long eventName) {
        setId(eventName);
        return this;
    }

    public EventDTO dateTime(long dateTime) {
        setId(dateTime);
        return this;
    }

    public EventDTO venue(long venue) {
        setId(venue);
        return this;
    }

    public EventDTO eventCancelled(long eventCancelled) {
        setId(eventCancelled);
        return this;
    }

    public EventDTO capacity(long capacity) {
        setId(capacity);
        return this;
    }

    public EventDTO cancellationFee(long cancellationFee) {
        setId(cancellationFee);
        return this;
    }

    public EventDTO ticketPrice(long ticketPrice) {
        setId(ticketPrice);
        return this;
    }
}
