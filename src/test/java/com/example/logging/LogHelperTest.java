package com.example.logging;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LogHelper class.
 * Tests event publishing, timestamp setting, and null publisher handling.
 * 
 * @author Mahaveer Ragbir
 */
public class LogHelperTest {

    @Test
    public void publishEventCreatesAndPublishesEvent() {
        SimpleEventPublisher publisher = new SimpleEventPublisher();
        SimpleEventListener listener = new SimpleEventListener();
        publisher.registerListener(listener);

        LogHelper.publishEvent(
                publisher,
                "case-1",
                "player-1",
                "START_GAME",
                null,
                null,
                null,
                null,
                null);

        assertEquals(1, listener.getEvents().size(), "One event should be published");
        assertEquals("START_GAME", listener.getEvents().get(0).getActivity(), "Event activity should match");
    }

    @Test
    public void publishEventHandlesNullPublisher() {
        LogHelper.publishEvent(
                null,
                "case-1",
                "player-1",
                "START_GAME",
                null, null, null, null, null);

        assertTrue(true);
    }

    @Test
    public void publishEventSetsTimestamp() {
        SimpleEventPublisher publisher = new SimpleEventPublisher();
        SimpleEventListener listener = new SimpleEventListener();
        publisher.registerListener(listener);

        Instant before = Instant.now();

        LogHelper.publishEvent(
                publisher, "case-1", "player-1", "TEST",
                null, null, null, null, null);

        Instant after = Instant.now();

        /** Check that events were received */
        assertFalse(listener.getEvents().isEmpty(), "Events list should not be empty");

        Instant eventTime = listener.getEvents().get(0).getTimestamp();

      
        assertFalse(eventTime.isBefore(before), "Event timestamp should not be before test start");
        assertFalse(eventTime.isAfter(after), "Event timestamp should not be after test end");
    }
}
