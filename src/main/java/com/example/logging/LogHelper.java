package com.example.logging;

import java.time.Instant;

/**
 * Utility helper for creating and publishing {@link EventRecord} instances.
 * <p>
 * This class centralizes the construction of {@code EventRecord} objects and
 * publishes them through a provided {@link EventPublisher}. It performs a
 * null-check on the publisher and stamps events with the current instant.
 *
 * @author Mahaveer Ragbir
 */
public class LogHelper {
    /**
     * Creates an {@link EventRecord} from the provided parameters and publishes
     * it via the given {@link EventPublisher}.
     *
     * <p>
     * If {@code publisher} is {@code null} this method returns without
     * performing any action.
     *
     * @param publisher      the {@link EventPublisher} used to publish the event
     * @param caseId         unique identifier for the event instance
     * @param playerId       identifier of the player who triggered the event
     * @param activity       description of the activity that occurred
     * @param category       category associated with the event (may be
     *                       {@code null})
     * @param questionValue  point value of the question related to the event (may
     *                       be {@code null})
     * @param answerGiven    the answer provided by the player (may be {@code null})
     * @param result         outcome of the event (e.g., {@code "correct"},
     *                       {@code "incorrect"})
     * @param scoreAfterPlay the player's score immediately after the event (may be
     *                       {@code null})
     */
    public static void publishEvent(EventPublisher publisher,
            String caseId,
            String playerId,
            String activity,
            String category,
            Integer questionValue,
            String answerGiven,
            String result,
            Integer scoreAfterPlay) {
        if (publisher == null)
            return;
        EventRecord ev = new EventRecord(
                caseId,
                playerId,
                activity,
                Instant.now(),
                category,
                questionValue,
                answerGiven,
                result,
                scoreAfterPlay);
        publisher.publish(ev);
    }
}
