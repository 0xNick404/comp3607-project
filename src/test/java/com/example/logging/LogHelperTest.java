package com.example.logging;

// import com.example.logging.LogHelper;
// import com.example.logging.SimpleEventListener;
// import com.example.logging.SimpleEventPublisher;
// import com.example.logging.SimpleEventListener;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(1, listener.getEvents().size());
        assertEquals("START_GAME", listener.getEvents().get(0).getActivity());
    }

    @Test
    public void publishEventHandlesNullPublisher() {
        // Should not crash
        LogHelper.publishEvent(
                null,
                "case-1",
                "player-1",
                "START_GAME",
                null, null, null, null, null);

        assertTrue(true); // No exception thrown
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
        Instant eventTime = listener.getEvents().get(0).getTimestamp();

        // Timestamp should be between before and after
        assertFalse(eventTime.isBefore(before));
        assertFalse(eventTime.isAfter(after));
    }
}