package com.oopsies.server.controller;
import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * TicketController class is a RestController that handles all the HTTP requests related to tickets.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
  @Autowired
  private TicketService ticketService;

  /**
   * Endpoint for retrieving all tickets associated with a specific booking.
   *
   * @param booking_id The ID of the booking.
   * @return A ResponseEntity containing a MessageResponse with the status code, a message, and a list of TicketDTOs.
   */
  @GetMapping(path = "/get/booking/{booking_id}")
  @PreAuthorize("hasAnyRole('ROLE_OFFICER') or hasAnyRole('ROLE_USER') or #user_id == authentication.principal.id")
  public ResponseEntity<?> getTicketsFromBooking(@PathVariable("booking_id") long booking_id) {
    List<TicketDTO> tickets = ticketService.getAllTicketsForBooking(booking_id);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "successful", tickets));
  }

  /**
   * Endpoint for retrieving all tickets associated with a specific user.
   *
   * @param user_id The ID of the user.
   * @return A ResponseEntity containing a MessageResponse with the status code, a message, and a list of TicketDTOs.
   */
  @GetMapping(path = "/get/user/{user_id}")
  @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
  public ResponseEntity<?> getTicketsByUser(@PathVariable("user_id") long user_id) {
    List<TicketDTO> tickets = ticketService.getAllTicketsForUser(user_id);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "successful", tickets));
  }

  /**
   * Endpoint for validating a ticket.
   *
   * @param ticket_id The ID of the ticket to validate.
   * @return A ResponseEntity containing a MessageResponse with the status code, a message, and a boolean indicating the validation result.
   */
  @PostMapping(path = "/validate/{ticket_id}")
  @PreAuthorize("hasAnyRole('ROLE_OFFICER')")
  public ResponseEntity<?> validateTicket(@PathVariable("ticket_id") long ticket_id) {
    try {
      boolean valid = ticketService.validateTicket(ticket_id);
      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
              200, "successful", valid));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
              200, e.getMessage(), false));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
              500, "failed", e.getMessage()));
    }
  }
}
