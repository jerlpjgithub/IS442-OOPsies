package com.oopsies.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    // Map this to event
    @Column(nullable = false)
    private int bookingId;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Ticket() {}

    public Long getId() {
        return this.ticketId;
    }

    public void setId(Long id) {
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

}
