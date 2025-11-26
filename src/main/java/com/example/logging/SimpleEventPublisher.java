package com.example.logging;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventPublisher implements EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();

    /**
     * @param listener
     *                 register a listener
     */
    @Override
    public void registerListener(EventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * @param listener
     *                 unregister a listener
     */
    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    /**
     * @param event
     *              publish an event to all registered listeners
     */
    @Override
    public void publish(EventRecord event) {
        List<EventListener> snapshot = new ArrayList<>(listeners);
        for (EventListener l : snapshot) {
            l.onEvent(event);
        }
    }

}
