package com.oopsies.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventCSVDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.User;
import com.oopsies.server.payload.request.EventRequest;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.util.DateUtil;
import java.util.ArrayList;

/**
 * Service for handling Event entities
 * This service contains methods for creating, finding
 * events by id or manager id, as well as updating and
 * cancelling events
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefundService refundService;

    @Lazy
    @Autowired
    private BookingService bookingService;

    @Autowired
    private DataService dataService;

    public EventService() { }

    /**
     * getEventById is a method that returns an Optional object that may contain an EventDTO
     * @param eventId is the id of the event being searched for
     * @return an Optional object containing an EventDTO if event is found, empty Optional otherwise
     */
    public Optional<EventDTO> getEventById(long eventId) {
        Optional<Event> event = eventRepository.findEventById(eventId);
        return event.map(this::convertToDTO);
    }

    /**
     * Retrieves a list of all events as Data Transfer Objects (DTOs).
     *
     * @return List of EventDTO containing all events.
     */
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Retrieves a list of EventCSVDTOs based on the manager's ID.
     *
     * @param event_manager_id The ID of the event manager.
     * @return List of EventCSVDTO containing events' CSV data.
     */
    public List<EventCSVDTO> getEventCSVDTOs(long event_manager_id){
        List<EventDTO> eventDTOs = this.getEventsByManagerId(event_manager_id);
        List<EventCSVDTO> eventCSVDTOs = new ArrayList<EventCSVDTO>();
        for(EventDTO eventDTO: eventDTOs){
            List<BookingDTO> bookings = bookingService.findBookingsByEventID(eventDTO.getId());
            int totalTicketsSold = dataService.getTotalTicketsSold(bookings);
            double totalRevenue = dataService.getTotalRevenue(bookings, eventDTO);
            int attendance = dataService.getAttendance(bookings);
            int getTotalTicketsRefunded = dataService.getNumRefunds(bookings);

            EventCSVDTO eventCsvDTO= new EventCSVDTO(
                                            eventDTO.getId(), 
                                            eventDTO.getEventName(), 
                                            eventDTO.getDateTime(), 
                                            eventDTO.getVenue(), 
                                            eventDTO.getCancelDate(),
                                            eventDTO.getCapacity(), 
                                            eventDTO.getCancellationFee(), 
                                            eventDTO.getTicketPrice(), 
                                            totalTicketsSold, 
                                            totalRevenue, 
                                            attendance, 
                                            getTotalTicketsRefunded
                                            );
            eventCSVDTOs.add(eventCsvDTO);
        }
        return eventCSVDTOs;
    }

    /**
     * Retrieves a list of events based on the manager's ID.
     *
     * @param manager_id The ID of the manager.
     * @return List of EventDTO containing events managed by the specified manager.
     * @throws IllegalArgumentException If the manager ID is not found.
     */
    public List<EventDTO> getEventsByManagerId(long manager_id) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(manager_id);

        if(!user.isPresent()){
            throw new IllegalArgumentException("User not found");
        }

        List<Event> events = eventRepository.findEventsByManagerId(manager_id);
        return events.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Creates a new event based on the provided EventRequest.
     *
     * @param eventRequest The request object containing event details.
     * @return EventDTO representing the created event.
     * @throws IllegalArgumentException If the manager is not found, unauthorized, or inputs are invalid.
     */
    public EventDTO createEvent(EventRequest eventRequest) {
        long managerId = eventRequest.getUserId();
        Optional<User> someUser = userRepository.findById(managerId);
        if (someUser.isEmpty()) {
            throw new IllegalArgumentException("Manager cannot be found!");
        }

        User user = someUser.get();
        boolean isManager = userDetailsService.isManager();
        boolean isAuthorised = userDetailsService.isAuthorisedUser(user);

        if (!isManager || !isAuthorised) {
            throw new IllegalArgumentException("Unauthorised content");
        }

        Event event = eventRequest.getEvent();
        validateInputs(event);

        // dateTime and Venue unique
        if (isEventExists(event)) {
            throw new IllegalArgumentException("There's already an event happening in that location at the same time!");
        }

        event.setManagerID(user);
        eventRepository.save(event);
        return convertToDTO(event);
    }

    /**
     * Updates an existing event based on the provided EventRequest and event ID.
     *
     * @param eventRequest The request object containing updated event details.
     * @param eventId      The ID of the event to update.
     * @return EventDTO representing the updated event.
     * @throws IllegalArgumentException If the manager is not found, unauthorized, event ID doesn't exist, or inputs are invalid.
     */
    public EventDTO updateEvent(EventRequest eventRequest, long eventId) {
        long managerId = eventRequest.getUserId();

        Optional<User> someUser = userRepository.findById(managerId);
        if (someUser.isEmpty()) {
            throw new IllegalArgumentException("ID " + managerId + " cannot be found!");
        }

        User user = someUser.get();
        boolean isManager = userDetailsService.isManager();
        boolean isAuthorised = userDetailsService.isAuthorisedUser(user);

        if (!isManager || !isAuthorised) {
            throw new IllegalArgumentException("Unauthorised content");
        }

        Event event = eventRequest.getEvent();

        Optional<Event> someExistingEvent = eventRepository.findEventById(eventId);
        if (someExistingEvent.isEmpty()) {
            throw new IllegalArgumentException("Event ID " + eventId + " doesn't exist!");
        }

        Event existingEvent = someExistingEvent.get();
        if (!userDetailsService.isAuthorisedUser(existingEvent.getManagerID())) {
            throw new IllegalArgumentException("Unauthorised content");
        }
        validateInputs(event);

        updateEvent(event, existingEvent);

        eventRepository.save(existingEvent);
        return convertToDTO(existingEvent);
    }

    /**
     * Updates the event details in the existing event object.
     *
     * @param event         The updated event details.
     * @param existingEvent The existing event object to update.
     */
    private void updateEvent(Event event, Event existingEvent) {
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setEventCancelled(event.getEventCancelDate());
        existingEvent.setCapacity(event.getCapacity());
        existingEvent.setCancellationFee(event.getCancellationFee());
        existingEvent.setTicketPrice(event.getTicketPrice());
    }

    /**
     * Validates the inputs for creating or updating an event.
     *
     * @param event The event object to validate.
     * @throws IllegalArgumentException If inputs are invalid.
     */
    private void validateInputs(Event event) {
        // ticket price > 0
        if (event.getTicketPrice() < 0) {
            throw new IllegalArgumentException("Ticket price must be more than 0!");
        }
        // event capacity > 0
        if (event.getCapacity() < 0) {
            throw new IllegalArgumentException("Capacity must be more than 0!");
        }
        // event cannot be created on the day itself, or before the specified event date
        if (new DateUtil().isBeforeToday(event.getDateTime())) {
            throw new IllegalArgumentException("Date should occur after today!");
        }
    }

    /**
     * Checks if an event already exists based on its date and venue.
     *
     * @param event The event to check uniqueness for.
     * @return Optional<Event> containing the existing event if found.
     */
    private Optional<Event> checkUniqueEvent(Event event) {
        return eventRepository.findEventByDateTimeAndVenue(event.getDateTime(), event.getVenue());
    }

    /**
     * Checks if an event already exists based on its date and venue.
     *
     * @param event The event to check uniqueness for.
     * @return true if the event exists; otherwise, false.
     */
    private boolean isEventExists(Event event) {
        return checkUniqueEvent(event).isPresent();
    }

    /**
     * Updates the event capacity by subtracting the specified count.
     *
     * @param eventDTO The EventDTO containing event details.
     * @param count    The count to subtract from the capacity.
     */
    public void updateEventCapacity(EventDTO eventDTO, int count) {
        Optional<Event> someEvent = eventRepository.findEventById(eventDTO.getId());
        assert someEvent.isPresent();
        Event event = someEvent.get();
        event.setCapacity(event.getCapacity() - count);
        eventRepository.save(event);
    }

    /**
     * Retrieves the Event object from the given EventDTO.
     *
     * @param eventDTO The EventDTO containing event details.
     * @return Event object corresponding to the EventDTO.
     */
    public Event getEventFromDTO(EventDTO eventDTO) {
        Optional<Event> someEvent = eventRepository.findEventById(eventDTO.getId());
        assert someEvent.isPresent();
        return someEvent.get();
    }

    /**
     * Cancels an event and initiates the refund process.
     *
     * @param event_id The ID of the event to cancel.
     */
    public void cancelEvent(long event_id){
        refundService.cancelEvent(event_id);
    }

    /**
     * Converts an Event object to its corresponding DTO.
     *
     * @param event The Event object to convert.
     * @return EventDTO representing the converted Event.
     */
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setEventName(event.getEventName());
        dto.setDateTime(event.getDateTime());
        dto.setVenue(event.getVenue());
        dto.setEventCancelled(event.getEventCancelDate());
        dto.setCapacity(event.getCapacity());
        dto.setCancellationFee(event.getCancellationFee());
        dto.setTicketPrice(event.getTicketPrice());
        dto.setTotalTicketsSold(0);
        return dto;
    }
    // dataService.getTotalTicketsSold(event.getId())
}

