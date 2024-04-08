package com.oopsies.server.controller;

import java.util.*;

import com.oopsies.server.services.BookingService;
import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.CsvDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Event;
import com.oopsies.server.payload.request.EventRequest;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping(path = "/create")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest){
        try {
            EventDTO eventDTO = eventService.createEvent(eventRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
                    201, "Event was created successfully!", eventDTO
            ));
        }
        catch (IllegalArgumentException exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
                    400, exc.getMessage(), null
            ));
        }
        catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }

    @PutMapping(path = "/update/{event_id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateEvent(@PathVariable("event_id") long event_id, @RequestBody EventRequest eventRequest){
        try {
            EventDTO eventDTO = eventService.updateEvent(eventRequest, event_id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
                    200, "Event was updated successfully!", eventDTO
            ));
        }
        catch (IllegalArgumentException exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
                    400, exc.getMessage(), null
            ));
        }
        catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }

    @GetMapping(path = "/get/{event_id}")
    public ResponseEntity<?> getEventsByEventId(@PathVariable("event_id") long event_id){
        Optional<EventDTO> _events = eventService.getEventById(event_id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _events
        ));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<?> getAllEvents(){
        List<EventDTO> _events = eventService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _events
        ));
    }

    @GetMapping(path = "/get/all/{event_manager_id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER') and #manager_id == authentication.principal.id")
    public ResponseEntity<?> getEventsByManagerId(@PathVariable("event_manager_id") long manager_id){
        try{
            List<EventDTO> _events = eventService.getEventsByManagerId(manager_id);

            if(_events.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                    200, "successful, no events created by this manager", _events
            )); 
            }
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                    200, "successful", _events
            ));
        }

        catch(Exception exc){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }


    @PostMapping(path = "/cancel/{event_id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<?> InitiateRefundByBookingId(@PathVariable("event_id") long event_id){
        try{
            eventService.cancelEvent(event_id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "Event cancelled successfully", null
        ));
        }
        catch(Exception exc){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }

    @GetMapping("/export/{event_id}")
    public void exportToCSV(HttpServletResponse response, @PathVariable("event_id") long event_id) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=event_bookings_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<CsvDTO> csvDTOs = bookingService.getCsvDTOForEvent(event_id);
        
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"booking_id", "booking_date", "cancel_date", "customer_full_name", "email" ,"no_of_tickets_booked"};
        String[] nameMapping = {"bookingID", "bookingDate", "cancelDate", "fullName", "email", "numOfTickets"};
        
        csvWriter.writeHeader(csvHeader);
        for (CsvDTO csvDTO : csvDTOs) {
            csvWriter.write(csvDTO, nameMapping);
        }
        csvWriter.close();
    }

}
