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
