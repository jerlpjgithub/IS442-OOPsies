package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumns({
//            @JoinColumn(name = "events_eventName"),
//            @JoinColumn(name = "events_dateTime"),
//            @JoinColumn(name = "events_venue")
//    })
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name = "bookingDate", nullable = false)
    private Date bookingDate;

    @Column(name = "cancelDate")
    private Date cancelDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    
    // For 1-1 with refund
    // @OneToOne(mappedBy = "booking")
    // private Refund refund;

    // Getters and Setters
    public long getBookingID() {
        return id;
    }

    public void setBookingID(long id) {
        this.id = id;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

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