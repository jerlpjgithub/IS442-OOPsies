package com.oopsies.server.entity;

import jakarta.persistence.*;

/**
 * The Ticket class represents an abstract class users within the system.
 * <p>
 * The class is annotated with JPA annotations to define the table mapping,
 * unique constraints,
 * and relationships with other entities.
 * <p>
 * JsonIdentityInfo is used to handle circular references correctly when
 * serializing entities to JSON.
 */
@Entity
@Table(name = "ticket")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long ticketId;

  // Map this to event
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id")
  private Booking booking;

  private boolean redeemed;

  @OneToOne
  @JoinColumn(name = "image_id")
  private Image image;

  public Ticket() {
  }

  public long getId() {
    return this.ticketId;
  }

  public void setId(long id) {
    this.ticketId = id;
  }

  public Booking getBooking() {
    return this.booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public Image getImage() {
    return this.image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public boolean isRedeemed() {
    return this.redeemed;
  }

  public void setRedeemed(boolean redeemed) {
    this.redeemed = redeemed;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "ticketId=" + ticketId +
        ", bookingId=" + booking +
        ", redeemed=" + redeemed +
        ", image=" + image +
        '}';
  }
}
