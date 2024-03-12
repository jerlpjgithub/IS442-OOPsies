package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @Column(name = "event")
    private Event event;

    @Temporal(TemporalType.DATE)
    @Column(name = "bookingDate")
    private Date bookingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "cancelDate")
    private Date cancelDate;

    @Column(name = "numTickets")
    private int numTickets;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Long userId;
    
    // For 1-1 with refund
    // @OneToOne(mappedBy = "booking")
    // private Refund refund;

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }
    // public int getEventID() {
    //     return eventID;
    // }

    public void setEvent(Event event) {
        this.event = event;
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

    public void setNumTickets(int numTickets){
        this.numTickets = numTickets;
    }
}