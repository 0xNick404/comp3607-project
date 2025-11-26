package com.example.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link EventListener} implementation that stores received
 * {@link EventRecord} instances in memory. This listener is primarily useful
 * for testing and lightweight in-process event collection.
 *
 * <p>
 * 
 * @author Mahaveer Ragbir
 *         </p>
 */
public class SimpleEventListener implements EventListener {
    private final List<EventRecord> events = new ArrayList<>();

    /**
     * Called when an {@link EventRecord} is published. The listener stores a
     * reference to the event in an internal list.
     *
     * @param event the published event to be recorded (must not be {@code null})
     */
    @Override
    public void onEvent(EventRecord event) {
        events.add(event);
    }

    /**
     * Returns a defensive copy of the events collected so far. Modifying the
     * returned list will not affect the listener's internal state.
     *
     * @return a new {@link List} containing the recorded {@link EventRecord}
     *         objects
     */
    public List<EventRecord> getEvents() {
        return new ArrayList<>(events);
    }

    /**
     * Clears all stored events from this listener.
     */
    public void clear() {
        events.clear();
    }
}
