package com.oopsies.server.payload.request;

import com.oopsies.server.entity.Event;

public class EventRequest {
    private Event event;
    private long userId;

    public EventRequest(Event event, long managerId) {
        this.event = event;
        this.userId = managerId;
    }

    public Event getEvent() {
        return event;
    }

    public long getUserId() {
        return userId;
    }
}
