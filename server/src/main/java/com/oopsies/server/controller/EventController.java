package com.oopsies.server.controller;

import java.util.*;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Event;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping(path = "/create-event")
    public ResponseEntity<?> createEvent(@RequestBody Event eventRequest){
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

    @PostMapping(path = "/update-event/{event_id}")
    public ResponseEntity<?> createEvent(@PathVariable("event_id") long event_id, @RequestBody Event eventRequest){
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

    @GetMapping(path = "/get-event/{event_id}")
    public ResponseEntity<?> getEventsByUserId(@PathVariable("event_id") long event_id){
        // try{
        Optional<EventDTO> _events = eventService.getEventById(event_id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _events
        ));
    }

    @GetMapping(path = "/get-event/all")
    public ResponseEntity<?> getAllEvents(){
        List<EventDTO> _events = eventService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _events
        ));
    }
}
