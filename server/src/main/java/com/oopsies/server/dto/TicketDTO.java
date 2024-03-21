package com.oopsies.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TicketDTO {
    @JsonProperty("User first name")
    private String first_name;
    @JsonProperty("User last name")
    private String last_name;
    @JsonProperty("User email")
    private String email;
    @JsonProperty("Event venue")
    private String event_venue;
    @JsonProperty("Event date and time")
    private Date event_dateTime;
    @JsonProperty("Ticket ID")
    private long ticket_id;
    @JsonProperty("Booking ID")
    private long booking_id;
    @JsonProperty("Booking Date")
    private Date booking_dateTime;
    @JsonProperty("Redeemed")
    private boolean redeemed;
    @JsonProperty("isValid")
    private boolean isValid;


    public TicketDTO() { }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public void setEvent_dateTime(Date event_dateTime) {
        this.event_dateTime = event_dateTime;
    }

    public void setTicket_id(long ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void setBooking_id(long booking_id) {
        this.booking_id = booking_id;
    }

    public void setBooking_dateTime(Date booking_dateTime) {
        this.booking_dateTime = booking_dateTime;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }
}
