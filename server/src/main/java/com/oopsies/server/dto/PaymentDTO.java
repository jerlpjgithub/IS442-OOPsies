package com.oopsies.server.dto;

import java.util.Date;

public class PaymentDTO {
    private int paymentID;
    private double amount;
    private Date paymentDate;

    // Constructor, getters, and setters

    public PaymentDTO() {
    }

    public PaymentDTO(int paymentID, double amount, Date paymentDate) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentDTO paymentID(int paymentID) {
        setPaymentID(paymentID);
        return this;
    }

    public PaymentDTO amount(double amount) {
        setAmount(amount);
        return this;
    }

    public PaymentDTO paymentDate(Date paymentDate) {
        setPaymentDate(paymentDate);
        return this;
    }
}
