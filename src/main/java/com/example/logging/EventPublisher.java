package com.example.logging;

/**
 * Interface for publishing events to registered listeners.
 * Implementations are responsible for managing the list of listeners
 * and notifying them whenever an {@link EventRecord} is published.
 *
 * @author Mahaveer Ragbir
 */
public interface EventPublisher {

    /**
     * Registers a new listener to receive published events.
     *
     * @param listener the listener to register
     */
    void registerListener(EventListener listener);

    /**
     * Unregisters an existing listener so it will no longer receive events.
     *
     * @param listener the listener to remove
     */
    void unregisterListener(EventListener listener);

    /**
     * Publishes an event to all currently registered listeners.
     *
     * @param event the event to broadcast
     */
    void publish(EventRecord event);

}
