package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @Column(name = "eventID")
    private int eventID;

    @Column(name = "ticketID")
    private int ticketID;

    @Column(name = "numTickets")
    private int numTickets;

    @Temporal(TemporalType.DATE)
    @Column(name = "bookingDate")
    private Date bookingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "cancelDate")
    private Date cancelDate;

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }
}