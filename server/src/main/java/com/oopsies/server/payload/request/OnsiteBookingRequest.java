package com.oopsies.server.payload.request;

/**
 * Payload for on-site Booking requests
 * Contains additional info of user's email
 * when performing operations on Booking
 */
public class OnsiteBookingRequest extends BookingRequest {
    String userEmail;

    public OnsiteBookingRequest(long eventId, int numTickets, String userEmail) {
        super(eventId, numTickets);
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "OnsiteBookingRequest{" +
                super.toString() +
                "userEmail='" + userEmail + '\'' +
                '}';
    }
}
