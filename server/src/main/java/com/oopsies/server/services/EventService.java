package com.oopsies.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.User;
import com.oopsies.server.payload.request.EventRequest;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.util.DateUtil;

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

    @Autowired
    private DataService dataService;

    public EventService() { }

    public Optional<EventDTO> getEventById(long eventId) {
        Optional<Event> event = eventRepository.findEventById(eventId);
        return event.map(this::convertToDTO);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDTO)
                .toList();
    }

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

    private void updateEvent(Event event, Event existingEvent) {
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setEventCancelled(event.getEventCancelDate());
        existingEvent.setCapacity(event.getCapacity());
        existingEvent.setCancellationFee(event.getCancellationFee());
        existingEvent.setTicketPrice(event.getTicketPrice());
    }

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

    public Optional<Event> checkUniqueEvent(Event event) {
        return eventRepository.findEventByDateTimeAndVenue(event.getDateTime(), event.getVenue());
    }

    private boolean isEventExists(Event event) {
        return checkUniqueEvent(event).isPresent();
    }

    public void updateEventCapacity(EventDTO eventDTO, int count) {
        Optional<Event> someEvent = eventRepository.findEventById(eventDTO.getId());
        assert someEvent.isPresent();
        Event event = someEvent.get();
        event.setCapacity(event.getCapacity() - count);
        eventRepository.save(event);
    }

    public Event getEventFromDTO(EventDTO eventDTO) {
        Optional<Event> someEvent = eventRepository.findEventById(eventDTO.getId());
        assert someEvent.isPresent();
        return someEvent.get();
    }

    public void cancelEvent(long event_id){
        refundService.cancelEvent(event_id);
    }

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

