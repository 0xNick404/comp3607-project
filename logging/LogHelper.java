package com.jeopardy.logging;

import java.time.Instant;

public class LogHelper {
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
