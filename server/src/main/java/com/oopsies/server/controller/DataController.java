package com.oopsies.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.BookingService;
import com.oopsies.server.services.DataService;
import com.oopsies.server.services.EventService;

/**
 * The DataController class handles HTTP requests related to data.
 * It uses the DataService, BookingService, and EventService to perform operations.
 */

@RestController
@RequestMapping("/data")
public class DataController {
    
    @Autowired
    private DataService dataService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    public DataController(DataService dataService) {
        this.dataService = dataService;

    }

    /**
     * Gets the total number of tickets sold for a specific event.
     *
     * @param eventId The ID of the event.
     * @return A ResponseEntity with the total number of tickets sold if the event exists, or a 404 Not Found status if the event does not exist.
     */
    @GetMapping("/totalTicketsSold/{event_id}")
    public ResponseEntity<?> getTotalTicketsSold(@PathVariable("event_id") long eventId) {

        Optional<EventDTO> optionalEvent = eventService.getEventById(eventId);

        if (optionalEvent.isPresent()) {
            List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventId);
            
            int totalTicketsSold = dataService.getTotalTicketsSold(bookings);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "Return Num Tickets Sold", totalTicketsSold));

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }


    /**
     * Gets the total revenue for a specific event.
     *
     * @param eventId The ID of the event.
     * @return A ResponseEntity with the total revenue if the event exists, or a 404 Not Found status if the event does not exist.
     */
    @GetMapping("/totalRevenue/{event_id}")
    public ResponseEntity<?> getTotalRevenue(@PathVariable("event_id") long eventId) {

        Optional<EventDTO> optionalEvent = eventService.getEventById(eventId);

        if (optionalEvent.isPresent()) {
            List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventId);
            EventDTO event = optionalEvent.get();

            double totalRevenue = dataService.getTotalRevenue(bookings, event);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "Return Total Revenue", totalRevenue));
            
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }        
    }

    /**
     * Gets the attendance for a specific event.
     *
     * @param eventId The ID of the event.
     * @return A ResponseEntity with the attendance count if the event exists, or a 404 Not Found status if the event does not exist.
     */  
    @GetMapping("/attendance/{event_id}")
    public ResponseEntity<?> getAttendance(@PathVariable("event_id") long eventId) {

        Optional<EventDTO> optionalEvent = eventService.getEventById(eventId);

        if (optionalEvent.isPresent()) {
            List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventId);
            int attendance = dataService.getAttendance(bookings);
            
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "Return Attendance", attendance));

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }
} 
