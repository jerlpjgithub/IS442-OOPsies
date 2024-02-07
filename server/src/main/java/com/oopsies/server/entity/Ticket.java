package com.oopsies.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Map this to event
    @Column(nullable = false)
    private int eventId;

    @Column(nullable = false)
    private double price;

    public Ticket() {}
}
