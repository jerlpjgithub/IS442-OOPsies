package com.oopsies.server.dto;

import java.util.Date;

/**
 * The PaymentDTO class represents a payment data transfer object.
 */
public class PaymentDTO {
    private long paymentID;
    private double amount;
    private Date paymentDate;

    /**
     * Default constructor for PaymentDTO.
     */
    public PaymentDTO() {
    }

    /**
     * Constructs a new PaymentDTO with the specified values.
     *
     * @param paymentID The ID of the payment.
     * @param amount The amount of the payment.
     * @param paymentDate The date of the payment.
     */
    public PaymentDTO(long paymentID, double amount, Date paymentDate) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    /**
     * Returns the ID of the payment.
     *
     * @return The ID of the payment.
     */
    public long getPaymentID() {
        return this.paymentID;
    }

    /**
     * Sets the ID of the payment.
     *
     * @param paymentID The ID of the payment.
     */
    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    /**
     * Returns the amount of the payment.
     *
     * @return The amount of the payment.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount The amount of the payment.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the date of the payment.
     *
     * @return The date of the payment.
     */
    public Date getPaymentDate() {
        return this.paymentDate;
    }

    /**
     * Sets the date of the payment.
     *
     * @param paymentDate The date of the payment.
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Sets the ID of the payment and returns this PaymentDTO.
     *
     * @param paymentID The ID of the payment.
     * @return This PaymentDTO.
     */
    public PaymentDTO paymentID(long paymentID) {
        setPaymentID(paymentID);
        return this;
    }

    /**
     * Sets the amount of the payment and returns this PaymentDTO.
     *
     * @param amount The amount of the payment.
     * @return This PaymentDTO.
     */
    public PaymentDTO amount(double amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Sets the date of the payment and returns this PaymentDTO.
     *
     * @param paymentDate The date of the payment.
     * @return This PaymentDTO.
     */
    public PaymentDTO paymentDate(Date paymentDate) {
        setPaymentDate(paymentDate);
        return this;
    }
}
