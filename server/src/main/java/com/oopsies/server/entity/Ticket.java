package com.oopsies.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    // Map this to event
    @Column(nullable = false)
    private int bookingId;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Ticket() {}

    public int getId() {
        return this.ticketId;
    }

    public void setId(int id) {
        this.ticketId = id;
    }

    public int getEventId() {
        return this.bookingId;
    }

    public void setEventId(int bookingId) {
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
