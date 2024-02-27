package com.oopsies.server.payments;

import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;

public interface Payments {
    default void processPayment(User user, Booking bookingDetails) throws UserInsufficientFundsException {
        double totalPrice = getTotalPrice(bookingDetails);
        if (!hasUserValidBalance(user, totalPrice)) {
            throw new UserInsufficientFundsException();
        }
        user.decrementAccountBalance(totalPrice);
    }

    default void processRefund(User user, Booking bookingDetails) {
        double totalPrice = getTotalPrice(bookingDetails);
        double cancellationFee = getCancellationFee(bookingDetails);
        user.incrementAccountBalance(totalPrice);

    }

    private double getCancellationFee(Booking bookingDetails) {
        Ticket[] tickets = bookingDetails.getTickets();
        double fee = 0;
        for (Ticket ticket: tickets) {
            fee += ticket.getCancellationFee();
        }
        return fee;
    }

    private double getTotalPrice(Booking bookingDetails) {
        Ticket[] tickets = bookingDetails.getTickets();
        double totalPrice = 0;
        for (Ticket ticket: tickets) {
            totalPrice += ticket.getPrice();
        }
        return totalPrice;
    }

    private boolean hasUserValidBalance(User user, double price) {
        return user.getAccountBalance() >= price;
    }
}
