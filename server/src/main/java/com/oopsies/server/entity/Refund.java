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

  /* Getters and Setters */

  public Long getRefundId() {
    return this.refundId;
  }

  public void setRefundId(Long refundId) {
    this.refundId = refundId;
  }

  public Booking getBooking() {
    return this.booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public Date getRefundDate() {
    return this.refundDate;
  }

  public void setRefundDate(Date refundDate) {
    this.refundDate = refundDate;
  }
}
