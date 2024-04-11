package com.oopsies.server.controller;
import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * TicketController class is a RestController that handles all the HTTP requests related to tickets.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
  @Autowired
  private TicketService ticketService;

  @GetMapping(path = "/get/booking/{booking_id}")
  @PreAuthorize("hasAnyRole('ROLE_OFFICER') or hasAnyRole('ROLE_USER') or #user_id == authentication.principal.id")
  public ResponseEntity<?> getTicketsFromBooking(@PathVariable("booking_id") long booking_id) {
    List<TicketDTO> tickets = ticketService.getAllTicketsForBooking(booking_id);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
        200, "successful", tickets));
  }

  @GetMapping(path = "/get/user/{user_id}")
  @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
  public ResponseEntity<?> getTicketsByUser(@PathVariable("user_id") long user_id) {
    List<TicketDTO> tickets = ticketService.getAllTicketsForUser(user_id);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
        200, "successful", tickets));
  }

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
