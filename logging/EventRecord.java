package com.jeopardy.logging;

import java.time.Instant;

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

    public String getCaseId() {
        return caseId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getActivity() {
        return activity;
    }

    public java.time.Instant getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public Integer getQuestionValue() {
        return questionValue;
    }

    public String getAnswerGiven() {
        return answerGiven;
    }

    public String getResult() {
        return result;
    }

    public Integer getScoreAfterPlay() {
        return scoreAfterPlay;
    }
}
