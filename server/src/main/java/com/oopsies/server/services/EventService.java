 package com.oopsies.server.services;

 import com.oopsies.server.dto.BookingDTO;
 import com.oopsies.server.dto.EventDTO;
 import com.oopsies.server.entity.Booking;
 import com.oopsies.server.entity.Event;
 import com.oopsies.server.repository.EventRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

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

     public void updateEventCapacity(EventDTO eventDTO, int count) {
         Event event = eventRepository.findEventById(eventDTO.getId()).get();
         event.setCapacity(event.getCapacity() - count);
         eventRepository.save(event);
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

