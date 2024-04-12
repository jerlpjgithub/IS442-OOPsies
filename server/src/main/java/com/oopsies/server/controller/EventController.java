package com.oopsies.server.controller;

import java.util.*;

import com.oopsies.server.services.BookingService;
import com.oopsies.server.services.DataService;
import com.oopsies.server.services.EmailService;
import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.CsvDTO;
import com.oopsies.server.dto.EventCSVDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.User;
import com.oopsies.server.payload.request.EventRequest;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import jakarta.servlet.http.HttpServletResponse;

/*
 * EventController class is a RestController that handles all the HTTP requests related to events.
 */
@RestController
@RequestMapping("/event")
public class EventController {

  @Autowired
  private EventService eventService;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private DataService dataService;

  @Autowired 
  private BookingRepository bookingRepository;

  @Autowired 
  private EmailService emailService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }
  /*
   * createEvent method is a POST request that creates an event.
   * @param eventRequest is the request body that contains the details of the event.
   * @return ResponseEntity that contains the status code, message and the event details.
   */
  @PostMapping(path = "/create")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
    try {
      EventDTO eventDTO = eventService.createEvent(eventRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
          201, "Event was created successfully!", eventDTO));
    } catch (IllegalArgumentException exc) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
          400, exc.getMessage(), null));
    } catch (Exception exc) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
          500, exc.getMessage(), null));
    }
  }

  /**
   * updateEvent method is a PUT request that updates an event.
   * @param event_id is the id of the event that needs to be updated.
   * @return ResponseEntity that contains the status code, message and the updated event details.
   */
  @PutMapping(path = "/update/{event_id}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateEvent(@PathVariable("event_id") long event_id,
    @RequestBody EventRequest eventRequest) {
    try {
      EventDTO eventDTO = eventService.updateEvent(eventRequest, event_id);
      return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
          200, "Event was updated successfully!", eventDTO));
    } catch (IllegalArgumentException exc) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
          400, exc.getMessage(), null));
    } catch (Exception exc) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
          500, exc.getMessage(), null));
    }
  }

  /**
   * getEventsByEventId method is a GET request that retrieves an event by its id.
   * @param event_id is the id of the event that needs to be retrieved.
   * @return ResponseEntity that contains the status code, message and the event details.
   */
  @GetMapping(path = "/get/{event_id}")
  public ResponseEntity<?> getEventsByEventId(@PathVariable("event_id") long event_id) {
    Optional<EventDTO> optionalEvent = eventService.getEventById(event_id);
    EventDTO _events = optionalEvent.get();

    List<BookingDTO> bookings = bookingService.findBookingsByEventID(event_id);
    int totalTicketsSold = dataService.getTotalTicketsSold(bookings);
    _events.setTotalTicketsSold(totalTicketsSold);

    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
        200, "successful", _events));
  }

  /**
   * getAllEvents method is a GET request that retrieves all the events.
   * @return ResponseEntity that contains the status code, message and the list of events.
   */
  @GetMapping(path = "/get/all")
  public ResponseEntity<?> getAllEvents() {

    List<EventDTO> _events = eventService.getAllEvents();

    for (EventDTO event : _events) {
      long eventId = event.getId();
      List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventId);
      int totalTicketsSold = dataService.getTotalTicketsSold(bookings);
      int attendance = dataService.getAttendance(bookings);
      int numRefunds = dataService.getNumRefunds(bookings);
      double totalRevenue = dataService.getTotalRevenue(bookings, event);

      event.setTotalTicketsSold(totalTicketsSold);
      event.setAttendance(attendance);
      event.setTotalTicketsRefunded(numRefunds);
      event.setTotalRevenue(totalRevenue);
    }

    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
        200, "successful", _events));
  }

  /**
   * getEventsByManagerId method is a GET request that retrieves all the events created by a manager.
   * @param manager_id is the id of the manager whose events need to be retrieved.
   * @return ResponseEntity that contains the status code, message and the list of events.
   */
  @GetMapping(path = "/get/all/{event_manager_id}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER') and #manager_id == authentication.principal.id")
  public ResponseEntity<?> getEventsByManagerId(@PathVariable("event_manager_id") long manager_id) {
    try {
      List<EventDTO> _events = eventService.getEventsByManagerId(manager_id);

      for (EventDTO event : _events) {
        long eventId = event.getId();
        List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventId);
        int totalTicketsSold = dataService.getTotalTicketsSold(bookings);
        int attendance = dataService.getAttendance(bookings);
        int numRefunds = dataService.getNumRefunds(bookings);
        double totalRevenue = dataService.getTotalRevenue(bookings, event);

        event.setTotalTicketsSold(totalTicketsSold);
        event.setAttendance(attendance);
        event.setTotalTicketsRefunded(numRefunds);
        event.setTotalRevenue(totalRevenue);
      }

      if (_events.isEmpty()) {
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "successful, no events created by this manager", _events));
      }
      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
          200, "successful", _events));
    }

    catch (Exception exc) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
          500, exc.getMessage(), null));
    }
  }

  /**
   * deleteEvent method is a POST request that deletes an event.
   * @param event_id is the id of the event that needs to be deleted.
   * @return ResponseEntity that contains the status code and message.
   */
  @PostMapping(path = "/cancel/{event_id}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseEntity<?> InitiateRefundByBookingId(@PathVariable("event_id") long event_id) {
    try {
      eventService.cancelEvent(event_id);

      List<Booking> listOfBookings = bookingRepository.findByEventId(event_id);
      for (Booking booking : listOfBookings) {

        User user = booking.getUser();

        emailService.createEmail(user, bookingService.convertToDTO(booking), "Refund Confirmation");
      }

      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
          200, "Event cancelled successfully", null));
    } catch (Exception exc) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
          500, exc.getMessage(), null));
    }
  }

  /** 
   * exportToCSV method is a GET request that exports the bookings of an event to a CSV file.
   * @param event_id is the id of the event whose bookings need to be exported.
   * @param response is the HttpServletResponse object that is used to write the CSV file.
   * @throws IOException if there is an error while writing the CSV file.
  */
  @GetMapping("/export/{event_id}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public void exportToCSV(HttpServletResponse response, @PathVariable("event_id") long event_id) throws IOException {
    response.setContentType("text/csv");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=event_bookings_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);

    List<CsvDTO> csvDTOs = bookingService.getCsvDTOForEvent(event_id);

    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
    String[] csvHeader = {"booking_id", "booking_date", "cancel_date", "customer_full_name", "email" ,"no_of_tickets_booked", "no_of_redeemed_tickets"};
    String[] nameMapping = {"bookingID", "bookingDate", "cancelDate", "fullName", "email", "numOfTickets", "numOfRedeemedTickets"};

    csvWriter.writeHeader(csvHeader);
    for (CsvDTO csvDTO : csvDTOs) {
      csvWriter.write(csvDTO, nameMapping);
    }
    csvWriter.close();
  }
  
  /**
   * exportAllEventCSV method is a GET request that exports all the events to a CSV file.
   * @param response is the HttpServletResponse object that is used to write the CSV file.
   * @param event_manager_id is the id of the manager whose events need to be exported.
   * @throws IOException if there is an error while writing the CSV file.
   */
  @GetMapping("/export/all/{event_manager_id}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public void exportAllEventCSV(HttpServletResponse response, @PathVariable("event_manager_id") long event_manager_id) throws IOException {
    response.setContentType("text/csv");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=all_events_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);

    List<EventCSVDTO> eventDTOs = eventService.getEventCSVDTOs(event_manager_id);

    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
    String[] csvHeader = {"event_id", "event_name", "event_date_time", "event_venue", "cancel_date", "capacity", "cancellation_fee", "ticket_price", "total_tickets_sold", "total_revenue", "attendance", "total_tickets_refunded"};
    String[] nameMapping = {"eventID", "eventName", "dateTime", "venue", "cancelDate", "capacity", "cancellationFee", "ticketPrice", "totalTicketsSold", "totalRevenue", "attendance", "totalTicketsRefunded"};
    csvWriter.writeHeader(csvHeader);

    for (EventCSVDTO eventCSVDTO : eventDTOs) {
      csvWriter.write(eventCSVDTO, nameMapping);
    }
    csvWriter.close();
  }

}
