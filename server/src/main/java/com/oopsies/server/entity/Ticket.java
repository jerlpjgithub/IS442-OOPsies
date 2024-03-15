package com.oopsies.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    // Map this to event
    @Column(nullable = false)
    private long bookingId;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Ticket() {}

    public long getId() {
        return this.ticketId;
    }

    public void setId(long id) {
        this.ticketId = id;
    }

    public long getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", bookingId=" + bookingId +
                ", image=" + image +
                '}';
    }
}
