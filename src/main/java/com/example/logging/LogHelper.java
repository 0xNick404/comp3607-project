package com.example.logging;

import java.time.Instant;

public class LogHelper {
    /**
     * @param publisher
     * @param caseId
     * @param playerId
     * @param activity
     * @param category
     * @param questionValue
     * @param answerGiven
     * @param result
     * @param scoreAfterPlay
     *                       Helper method to create and publish an event
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
