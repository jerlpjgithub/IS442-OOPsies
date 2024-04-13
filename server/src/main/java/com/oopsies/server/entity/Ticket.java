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

/**
 * Returns the ID of the Ticket.
 *
 * @return A long representing the ID of the Ticket.
 */
public long getId() {
    return this.ticketId;
}

/**
 * Sets the ID of the Ticket.
 *
 * @param id The ID to set.
 */
public void setId(long id) {
    this.ticketId = id;
}

/**
 * Returns the Booking associated with the Ticket.
 *
 * @return A Booking object representing the booking.
 */
public Booking getBooking() {
    return this.booking;
}

/**
 * Sets the Booking associated with the Ticket.
 *
 * @param booking The Booking to set.
 */
public void setBooking(Booking booking) {
    this.booking = booking;
}

/**
 * Returns the Image associated with the Ticket.
 *
 * @return An Image object representing the image.
 */
public Image getImage() {
    return this.image;
}

/**
 * Sets the Image associated with the Ticket.
 *
 * @param image The Image to set.
 */
public void setImage(Image image) {
    this.image = image;
}

/**
 * Returns the redeemed status of the Ticket.
 *
 * @return A boolean representing the redeemed status.
 */
public boolean isRedeemed() {
    return this.redeemed;
}

/**
 * Sets the redeemed status of the Ticket.
 *
 * @param redeemed The redeemed status to set.
 */
public void setRedeemed(boolean redeemed) {
    this.redeemed = redeemed;
}

/**
 * Returns a string representation of the Ticket.
 *
 * @return A string representation of the Ticket.
 */
@Override
public String toString() {
    return "Ticket{" +
        "ticketId=" + ticketId +
        ", bookingId=" + booking +
        ", redeemed=" + redeemed +
        ", image=" + image +
        '}';
}}
