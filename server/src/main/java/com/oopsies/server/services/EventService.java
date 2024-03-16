 package com.oopsies.server.services;

 import com.oopsies.server.dto.BookingDTO;
 import com.oopsies.server.dto.EventDTO;
 import com.oopsies.server.entity.Booking;
 import com.oopsies.server.entity.Event;
 import com.oopsies.server.repository.EventRepository;
 import com.oopsies.server.util.DateUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.Date;
 import java.util.List;
 import java.util.Optional;

 @Service
 public class EventService {

     @Autowired
     private EventRepository eventRepository;

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

     public EventDTO createEvent(Event event) {
         validateInputs(event);
         // eventName, dateTime and Venue unique
         if (isEventExists(event)) {
             throw new IllegalArgumentException("Event already exists!");
         }

         eventRepository.save(event);
         return convertToDTO(event);
     }

     public EventDTO updateEvent(Event event, long eventId) {
         Optional<Event> someExistingEvent = eventRepository.findEventById(eventId);
         if (someExistingEvent.isEmpty()) {
             throw new IllegalArgumentException("Event ID " + eventId + " doesn't exist!");
         }
         validateInputs(event);

         Event existingEvent = someExistingEvent.get();
         updateEvent(event, existingEvent);

         eventRepository.save(existingEvent);
         return convertToDTO(existingEvent);
     }

     private void updateEvent(Event event, Event existingEvent) {
         existingEvent.setEventName(event.getEventName());
         existingEvent.setManagerID(event.getManagerID());
         existingEvent.setDateTime(event.getDateTime());
         existingEvent.setVenue(event.getVenue());
         existingEvent.setEventCancelled(event.getEventCancelled());
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
         // TODO check if need to set minimum duration between creation and event occurrence
         if (new DateUtil().isBeforeToday(event.getDateTime())) {
             throw new IllegalArgumentException("Date should occur after today!");
         }
     }

     public Optional<Event> getEvent(Event event) {
         return eventRepository.findEventByEventNameAndDateTimeAndVenue(event.getEventName(), event.getDateTime(), event.getVenue());
     }

     private boolean isEventExists(Event event) {
         return getEvent(event).isPresent();
     }

     public void updateEventCapacity(EventDTO eventDTO, int count) {
         Event event = eventRepository.findEventById(eventDTO.getId()).get();
         event.setCapacity(event.getCapacity() - count);
         eventRepository.save(event);
     }

     public Event getEventFromDTO(EventDTO eventDTO) {
         return eventRepository.findEventById(eventDTO.getId()).get();
     }

     private EventDTO convertToDTO(Event event) {
         EventDTO dto = new EventDTO();
         dto.setId(event.getId());
         dto.setEventName(event.getEventName());
         dto.setDateTime(event.getDateTime());
         dto.setVenue(event.getVenue());
         dto.setEventCancelled(event.getEventCancelled());
         dto.setCapacity(event.getCapacity());
         dto.setCancellationFee(event.getCancellationFee());
         dto.setTicketPrice(event.getTicketPrice());
         return dto;
     }
 }

