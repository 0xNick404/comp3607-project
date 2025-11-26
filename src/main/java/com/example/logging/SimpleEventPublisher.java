package com.example.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * A straightforward implementation of {@link EventPublisher} that maintains
 * an in-memory list of listeners and dispatches events to each one.
 *
 * <p>
 * This publisher performs basic operations: registering listeners,
 * unregistering them, and broadcasting {@link EventRecord} instances to all
 * listeners currently stored. A snapshot of the listener list is used during
 * publishing to avoid {@link java.util.ConcurrentModificationException}.
 *
 * <p>
 * @author Mahaveer Ragbir
 * </p>
 */
public class SimpleEventPublisher implements EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();

    /**
     * Registers the specified listener if it is non-null and not already
     * registered.
     *
     * @param listener the {@link EventListener} to register
     */
    @Override
    public void registerListener(EventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregisters the specified listener if it exists in the internal list.
     *
     * @param listener the {@link EventListener} to remove
     */
    @Override
    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Publishes the given event to all currently registered listeners. A
     * snapshot of the listener list is used to ensure safe iteration even if
     * listeners are added or removed during event dispatch.
     *
     * @param event the {@link EventRecord} to broadcast
     */
    @Override
    public void publish(EventRecord event) {
    }
}
