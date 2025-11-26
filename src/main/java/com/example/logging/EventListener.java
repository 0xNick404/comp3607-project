package com.example.logging;

/**
 * Interface for objects that listen to and respond to event records.
 * Implementations of this interface define how events are handled,
 * such as logging to files, storing in memory, or triggering other actions.
 * 
 * @author Mahaveer Ragbir
 */
public interface EventListener {
    /**
     * Called when an event is published.
     * Implementations should define the specific action to take when receiving an event.
     * 
     * @param event the event record containing details about the event
     */
    void onEvent(EventRecord event);
}
