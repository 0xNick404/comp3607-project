package com.example.logging;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventPublisher implements EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void registerListener(EventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void publish(EventRecord event) {
        List<EventListener> snapshot = new ArrayList<>(listeners);
        for (EventListener l : snapshot) {
            l.onEvent(event);
        }
    }

}
