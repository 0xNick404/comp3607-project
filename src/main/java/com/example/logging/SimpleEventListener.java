package com.example.logging;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventListener implements EventListener {
    private final List<EventRecord> events = new ArrayList<>();

    /**
     * @param event
     *              onEvent method to store received events
     */
    @Override
    public void onEvent(EventRecord event) {
        events.add(event);
    }

    /**
     * @return returns list of events received
     */
    public List<EventRecord> getEvents() {
        return new ArrayList<>(events);
    }

    public void clear() {
        events.clear();
    }
}