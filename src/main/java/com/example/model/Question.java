package com.example.model;

/**
 * Represents a question with a category, value, question text, multiple choice answer options and correct answer.
 * Tracks whether the question has been selected or not.
 * @author Nicholas Grimes
 */
public class Question{
    private String category;
    private int value;
    private String questionText;
    private String[] options;
    private String correctAnswer;
    private boolean hasBeenPicked;

    /**
     * Creates a new {@link Question} instance.
     * Accepts {@code value} as String then parses to Integer
     * @param category the category of the question
     * @param value the monetary value of the question as a string
     * @param questionText the question prompt
     * @param options an array of multiple-choice answers
     * @param correctAnswer the correct answer from the options array
     */
    public Question(String category, String value, String questionText, String[] options, String correctAnswer){
        this.category = category;

        try{
            this.value = Integer.parseInt(value);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }

        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.hasBeenPicked = false;
    }

    /**
     * Accessor Method for {@code private String category;}
     * Returns the category of the question.
     * @return the category name
     */
    public String getCategory(){return this.category;}

    /**
     * Accessor Method for {@code private int value;}
     * Returns the monetary value associated with the question.
     * @return the question value
     */
    public int getValue(){return this.value;}

    /**
     * Accessor Method for {@code private String questionText;}
     * Returns the text of the question prompt.
     * @return the question text
     */
    public String getQuestionText(){return this.questionText;}

    /**
     * Accessor Method for {@code private String[] options;}
     * Returns all multiple-choice answer options.
     * @return an array of possible answers
     */
    public String[] getOptions(){return this.options;}

    /**
     * Accessor Method for {@code private String correctAnswer;}
     * RReturns the correct answer for this question.
     * @return the correct answer string
     */
    public String getCorrectAnswer(){return this.correctAnswer;}

    /**
     * Accessor Method for {@code private private boolean hasBeenPicked;}
     * Indicates whether this question has already been selected during the game.
     * @return {@code true} if the question has been picked, otherwise {@code false}
     */
    public boolean hasBeenPicked(){return this.hasBeenPicked;}

    /**
     * Marks this question as having been selected by a player.
     */
    public void markPicked(){this.hasBeenPicked = true;}
}
