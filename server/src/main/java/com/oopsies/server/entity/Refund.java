package com.oopsies.server.entity;


import jakarta.persistence.*;
import java.util.Date;

/**
 * The Refund class represents an abstract class users within the system.
 * <p>
 * The class is annotated with JPA annotations to define the table mapping,
 * unique constraints,
 * and relationships with other entities.
 * <p>
 * JsonIdentityInfo is used to handle circular references correctly when
 * serializing entities to JSON.
 */
@Entity
@Table(name = "refund")
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long refundId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id")
  private Booking booking;

  @Temporal(TemporalType.DATE)
  @Column(name = "refundDate")
  private Date refundDate;

  /* Constructors */
  public Refund() {
  }

  public Refund(Booking booking, Date refundDate) {
    this.booking = booking;
    this.refundDate = refundDate;
  }

  /**
   * Returns the ID of the Refund.
   *
   * @return A Long representing the ID of the Refund.
   */
  public Long getRefundId() {
    return this.refundId;
  }

  /**
   * Sets the ID of the Refund.
   *
   * @param refundId The ID to set.
   */
  public void setRefundId(Long refundId) {
    this.refundId = refundId;
  }

  /**
   * Returns the Booking associated with the Refund.
   *
   * @return A Booking object representing the booking.
   */
  public Booking getBooking() {
    return this.booking;
  }

  /**
   * Sets the Booking associated with the Refund.
   *
   * @param booking The Booking to set.
   */
  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  /**
   * Returns the refund date of the Refund.
   *
   * @return A Date object representing the refund date.
   */
  public Date getRefundDate() {
    return this.refundDate;
  }

  /**
   * Sets the refund date of the Refund.
   *
   * @param refundDate The refund date to set.
   */
  public void setRefundDate(Date refundDate) {
    this.refundDate = refundDate;
  }
}
