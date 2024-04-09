package com.oopsies.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.EmailStructure;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.TicketRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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

    public void sendEmail(String toMail, EmailStructure emailStructure) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
        helper.setFrom(fromMail);
        helper.setTo(toMail);
        helper.setSubject(emailStructure.getSubject());
        helper.setText(emailStructure.getMessage(), true);

        emailSender.send(mimeMessage);
    }

    public void createEmail(User user, BookingDTO bookingDTO, String type) throws MessagingException {
        //get booking parameters first
        String name = user.getFirstName() + " " + user.getLastName();
         String email = user.getEmail();
        // String email = "laiu.asher@gmail.com"; 
        //this will need to change when we have proper user emails
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