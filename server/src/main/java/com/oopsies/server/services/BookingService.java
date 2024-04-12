package com.oopsies.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.CsvDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.User;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.util.DateUtil;

/**
 * Service for handling Booking entities.
 * This service provides methods for creating bookings,
 * finding bookings, and generating csv statistics
 */
@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private EventService eventService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private TicketService ticketService;

  @Autowired
  private RefundService refundService;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  /**
   * Creates a new booking for a specified user at an event with a given number of tickets.
   *
   * @param user_id    The ID of the user making the booking.
   * @param eventId    The ID of the event to book tickets for.
   * @param numTickets The number of tickets to book.
   * @return BookingDTO representing the created booking.
   * @throws UserInsufficientFundsException If the user does not have sufficient funds.
   */
  public BookingDTO createBooking(long user_id, long eventId, int numTickets) throws UserInsufficientFundsException {
    // totalGuests are the booker + their friends
    Optional<User> someUser = userDetailsService.getUserById(user_id);
    if (someUser.isEmpty()) {
      throw new IllegalArgumentException("User ID " + user_id + " cannot be found!");
    }

    // Find the event
    Optional<EventDTO> someEvent = eventService.getEventById(eventId);
    if (someEvent.isEmpty()) {
      throw new IllegalArgumentException("Event not found");
    }

    User user = someUser.get();
    boolean isManager = userDetailsService.isManager();
    if (isManager) {
      throw new IllegalArgumentException("Managers cannot purchase tickets for event you are managing!");
    }

    boolean isOfficer = userDetailsService.isOfficer();
    EventDTO event = someEvent.get();
    double totalPriceOfTickets = numTickets * event.getTicketPrice(); 

    if (!isOfficer) {
      DateUtil dateUtil = new DateUtil();
      Date eventDate = event.getDateTime();
      if (dateUtil.isLessThanTwentyFourHours(eventDate) || dateUtil.isMoreThanSixMonths(eventDate)) {
        throw new IllegalArgumentException("Customers can book tickets up to 6 months in advance and no later than 24 hours before the event start time");
      }
      if (numTickets > 5) {
        throw new IllegalArgumentException("Cannot purchase more than 5 tickets");
      }
    }
    if (event.getCapacity() < numTickets) {
      throw new IllegalArgumentException("Event capacity is less than number of guests");
    }
    if (getCurrentGuestCount(user_id, eventId) + numTickets > 5) {
      throw new IllegalArgumentException("Cannot purchase more than 5 tickets");
    }
    if(!hasUserValidBalance(user, totalPriceOfTickets)){
      throw new UserInsufficientFundsException();
    }

    // Reduce event capacity by numGuests + the og booker
    Booking booking = new Booking();
    booking.setUser(user);
    booking.setBookingDate(new Date());
    booking.setEvent(eventService.getEventFromDTO(event));
    booking.setCancelDate(null);
    Booking newBooking = bookingRepository.save(booking);
    eventService.updateEventCapacity(event, numTickets);

    paymentService.processPayment(user, booking, event, numTickets);

    for (int i = 0; i < numTickets; i++) {
      ticketService.createNewTicket(newBooking);
    }

    return convertToDTO(booking);
  }

  /**
   * Retrieves the current guest count for a specific user at an event.
   *
   * @param userId  The ID of the user.
   * @param eventId The ID of the event.
   * @return The current guest count for the user at the event.
   */
  private int getCurrentGuestCount(long userId, long eventId) {
    List<Booking> currentBookings = bookingRepository.findByUserIdAndEventId(userId, eventId);
    int guestCount = 0;
    for (Booking b : currentBookings) {
      List<TicketDTO> tickets = ticketService.getAllTicketsForBooking(b);
      guestCount += tickets.size();
    }
    return guestCount;
  }

  /**
   * Finds all bookings made by a specific user.
   *
   * @param userId The ID of the user.
   * @return List of BookingDTO representing the bookings made by the user.
   */
  public List<BookingDTO> findBookingsByUserId(long userId) {
    List<Booking> bookings = bookingRepository.findByUserId(userId);
    return bookings.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  /**
   * Processes a booking refund for the specified booking ID.
   *
   * @param booking_id The ID of the booking to be refunded.
   * @throws Exception If there is an issue processing the refund.
   */
  public void processBookingRefund(long booking_id) throws Exception{
    refundService.processRefund(booking_id);
  }

  /**
   * Finds all bookings made for a specific event.
   *
   * @param eventID The ID of the event.
   * @return List of BookingDTO representing the bookings made for the event.
   */
  public List<BookingDTO> findBookingsByEventID(long eventID) {
    List<Booking> bookings = bookingRepository.findByEventId(eventID);
    return bookings.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());    
  }

  /**
   * Converts a Booking entity to its corresponding DTO.
   *
   * @param booking The Booking entity to convert.
   * @return BookingDTO representing the converted Booking entity.
   */
  public BookingDTO convertToDTO(Booking booking) {
    BookingDTO dto = new BookingDTO();
    dto.setBookingID(booking.getBookingID());
    dto.setBookingDate(booking.getBookingDate());
    dto.setCancelDate(booking.getCancelDate());

    // get numTickets
    int numTickets = ticketService.getAllTicketsForBooking(booking).size();
    dto.setNumTickets(numTickets);
    Optional<EventDTO> event = eventService.getEventById(booking.getEvent().getId());
    assert event.isPresent();
    dto.setEvent(event.get());

    return dto;
  }

  /**
   * Retrieves CSV data for bookings related to a specific event.
   *
   * @param event_id The ID of the event.
   * @return List of CsvDTO containing CSV data for bookings related to the event.
   */
  public List<CsvDTO> getCsvDTOForEvent(long event_id) {
    List<Booking> bookings = bookingRepository.findByEventId(event_id);
    
    List<CsvDTO> csvDTOs = new ArrayList<>();

    for(Booking booking: bookings){
      int numOfTicketsToBooking = ticketService.getAllTicketsForBooking(booking.getBookingID()).size();
      int redeemedTickets = ticketService.getAllTicketsForBooking(booking.getBookingID()).stream().filter(TicketDTO::isRedeemed).toList().size();
      User userThatBooked = booking.getUser();
      CsvDTO CsvDTO = new CsvDTO(booking.getBookingID(), booking.getBookingDate(), booking.getCancelDate(), userThatBooked.getFirstName() + " "+ userThatBooked.getLastName(), userThatBooked.getEmail(), numOfTicketsToBooking, redeemedTickets);
      csvDTOs.add(CsvDTO);
    }

    return csvDTOs;
  }

  /**
   * Checks if a user has a valid balance for a given price.
   *
   * @param user  The user to check.
   * @param price The price to check against the user's balance.
   * @return true if the user has a valid balance; otherwise, false.
   */
  private boolean hasUserValidBalance(User user, double price) {
        return user.getAccountBalance() >= price;
  }
}


