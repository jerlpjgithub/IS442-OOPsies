package com.oopsies.server.entity;

import jakarta.persistence.*;

import java.util.Date;

/**
 * The Payment class represents an abstract class users within the system.
 * <p>
 * The class is annotated with JPA annotations to define the table mapping,
 * unique constraints,
 * and relationships with other entities.
 * <p>
 * JsonIdentityInfo is used to handle circular references correctly when
 * serializing entities to JSON.
 */
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date paymentDate;

    public Payment(){ }

    public Payment(Booking booking, double amount,
                   Date paymentDate) {
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // --------------- Getters and Setters (start) ------------------

    /**
     * Returns the ID of the Payment.
     *
     * @return A long representing the ID of the Payment.
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the ID of the Payment.
     *
     * @param paymentId The ID to set.
     */
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Returns the Booking associated with the Payment.
     *
     * @return A Booking object representing the booking.
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Sets the Booking associated with the Payment.
     *
     * @param booking The Booking to set.
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Returns the amount of the Payment.
     *
     * @return A double representing the amount of the Payment.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the Payment.
     *
     * @param amount The amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the payment date of the Payment.
     *
     * @return A Date object representing the payment date.
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the payment date of the Payment.
     *
     * @param paymentDate The payment date to set.
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    // --------------- Getters and Setters (end) ------------------

    /**
     * Returns a string representation of the Payment object.
     *
     * @return A string representation of the Payment object.
     */
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", userId=" + booking.getBookingID() +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }

}
