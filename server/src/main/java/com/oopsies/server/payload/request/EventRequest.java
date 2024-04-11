package com.oopsies.server.payload.request;

import com.oopsies.server.entity.Event;

/**
 * Payload for Event requests
 * Contains details of the event being operated on
 * and its managerId for validation
 */
public class EventRequest {
    private Event event;
    private long managerId;

    public EventRequest(Event event, long managerId) {
        this.event = event;
        this.managerId = managerId;
    }

    public Event getEvent() {
        return event;
    }

    public long getUserId() {
        return managerId;
    }
}
