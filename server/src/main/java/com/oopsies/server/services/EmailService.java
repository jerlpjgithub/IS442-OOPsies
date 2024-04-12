package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.EmailStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The EmailService class provides methods for sending emails.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Value("${spring.mail.username}")
    private String fromMail;

    /**
     * Sends an email.
     *
     * @param toMail The email address to send the email to.
     * @param emailStructure The structure of the email to send.
     * @throws MessagingException If an error occurs while sending the email.
     */
    public void sendEmail(String toMail, EmailStructure emailStructure) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
        helper.setFrom(fromMail);
        helper.setTo(toMail);
        helper.setSubject(emailStructure.getSubject());
        helper.setText(emailStructure.getMessage(), true);

        emailSender.send(mimeMessage);
    }

    /**
     * Creates an email for a user and a booking or refund.
     *
     * @param user The user to create the email for.
     * @param bookingDTO The booking to create the email for.
     * @param type The type of the email.
     * @throws MessagingException If an error occurs while creating the email.
     */
    public void createEmail(User user, BookingDTO bookingDTO, String type) throws MessagingException {
        //get booking parameters first
        String name = user.getFirstName() + " " + user.getLastName();
        String email = user.getEmail();
        long bookingID = bookingDTO.getBookingID();
        Date bookingDate = bookingDTO.getBookingDate();

        //get event parameters
        EventDTO event = bookingDTO.getEvent();

        String eventName = event.getEventName();
        Date eventDate = event.getDateTime();
        Date refundDate = bookingDTO.getCancelDate();
        String venue = event.getVenue();

        //get ticket parameters
        List<Ticket> tickets = ticketRepository.findTicketsByBooking(bookingRepository.findBookingById(bookingID));
        List<Long> ticketIDs = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketIDs.add(ticket.getId());
        }

        EmailStructure emailStructure;
        double ticketPrice = event.getTicketPrice();
        double totalPrice = ticketPrice * tickets.size();

        if (type.equals("Booking Confirmation")) {

            emailStructure = new EmailStructure(name, email, bookingID, bookingDate, eventName,
                    eventDate, refundDate, venue, ticketIDs,
                    totalPrice, 0, type);
        } else {
            double penaltyFee = event.getCancellationFee();
            double refundedAmount = totalPrice - penaltyFee;

            emailStructure = new EmailStructure(name, email, bookingID, bookingDate, eventName,
                    eventDate, refundDate, venue, ticketIDs, refundedAmount,
                    penaltyFee, type);
        }

        sendEmail(email, emailStructure);
    }
}