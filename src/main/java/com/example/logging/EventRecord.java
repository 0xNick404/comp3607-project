package com.example.logging;

import java.time.Instant;

/**
 * Represents a single event within the logging system.
 * Each event captures player activity, metadata, and game-related details
 * that can later be recorded or analyzed.
 *
 * @author Mahaveer Ragbir
 */
public class EventRecord {
    private final String caseId;
    private final String playerId;
    private final String activity;
    private final Instant timestamp;
    private final String category;
    private final Integer questionValue;
    private final String answerGiven;
    private final String result;
    private final Integer scoreAfterPlay;

    /**
     * Constructs a new {@code EventRecord} with all required metadata.
     *
     * @param caseId          unique identifier for the event instance
     * @param playerId        identifier of the player who performed the action
     * @param activity        description of the type of event or action
     * @param timestamp       time at which the event occurred
     * @param category        question category involved in the event (if any)
     * @param questionValue   point value of the question (if applicable)
     * @param answerGiven     the player's response (if applicable)
     * @param result          outcome of the event (e.g., "correct", "incorrect")
     * @param scoreAfterPlay  the player's score after the event occurred
     */
    public EventRecord(String caseId,
                       String playerId,
                       String activity,
                       Instant timestamp,
                       String category,
                       Integer questionValue,
                       String answerGiven,
                       String result,
                       Integer scoreAfterPlay) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = timestamp;
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfterPlay = scoreAfterPlay;
    }

    /**
     * @return the unique identifier for this event
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * @return the ID of the player who generated this event
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @return a description of the event's activity type
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @return the timestamp at which this event occurred
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @return the category associated with the event
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the point value of the related question
     */
    public Integer getQuestionValue() {
        return questionValue;
    }

    /**
     * @return the answer provided by the player
     */
    public String getAnswerGiven() {
        return answerGiven;
    }

    /**
     * @return the result of the event (e.g., "correct", "incorrect")
     */
    public String getResult() {
        return result;
    }

    /**
     * @return the player's score immediately after this event
     */
    public Integer getScoreAfterPlay() {
        return scoreAfterPlay;
    }
}
