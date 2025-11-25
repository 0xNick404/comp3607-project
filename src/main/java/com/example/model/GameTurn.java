package com.example.model;

import com.example.model.Player;
/**
 * Represents a single turn in a Jeopardy game, capturing all relevant details.
 * This includes the player taking the turn, the question asked, the answer given,
 * and the resulting score changes.
 * 
 */

public class GameTurn {
    
    private final Player player;
    private final String category;
    private final int questionValue;
    private final String questionText;
    private final String givenAnswer;

    private final boolean isCorrect;
    private final int pointsEarned;
    private final int runningTotal;

    /**
     * Constructs a GameTurn with all necessary details.
     * @param player        The player taking the turn.
     * @param category      The category of the question.
     * @param questionValue The monetary value of the question.
     * @param questionText  The text of the question asked.
     * @param givenAnswer   The answer provided by the player.
     * @param isCorrect     Whether the given answer was correct.
     * @param pointsEarned  Points earned (or lost) this turn.
     * @param runningTotal  The player's total score after this turn.
     * 
     */

    public GameTurn(Player player, String category, int questionValue, String questionText,
                    String givenAnswer, boolean isCorrect, int pointsEarned, int runningTotal) {
        this.player = player;
        this.category = category;
        this.questionValue = questionValue;
        this.questionText = questionText;
        this.givenAnswer = givenAnswer;
        this.isCorrect = isCorrect;
        this.pointsEarned = pointsEarned;
        this.runningTotal = runningTotal;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCategory() {
        return category;
    }

    public int getQuestionValue() {
        return questionValue;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public int getRunningTotal() {
        return runningTotal;
    }

}
