package com.example.model;

/**
 * Represents a question with a category, value, question text, multiple choice
 * answer options, and correct answer.
 * Tracks whether the question has been selected or not.
 */

public class Question {
    private String category;
    private int value;
    private String questionText;
    private String[] options;
    private String correctAnswer;
    private boolean hasBeenPicked;

    public Question(String category, String value, String questionText, String[] options, String correctAnswer) {
        this.category = category;

        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.value = 0;
        }

        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.hasBeenPicked = false;
    }

    public String getCategory() {
        return this.category;
    }

    public int getValue() {
        return this.value;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String[] getOptions() {
        return this.options;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public boolean hasBeenPicked() {
        return this.hasBeenPicked;
    }

    public void markPicked() {
        this.hasBeenPicked = true;
    }
}
