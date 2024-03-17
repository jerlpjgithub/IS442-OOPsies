package com.oopsies.server.entity;


import jakarta.persistence.*;
import java.util.Date;

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

  public Refund(Long refundId, Booking booking, Date refundDate) {
    this.refundId = refundId;
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
