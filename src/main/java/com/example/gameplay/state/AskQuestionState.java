package com.example.gameplay.state;

import com.example.model.Question;

/**
 * Concrete class responsible for displaying the chosen question and its multiple-choice answer options to the player.
 * After showing the question, the state transitions to the accept answer state.
 * @author Nicholas Grimes
 */
public class AskQuestionState implements GameState{
    /**
     * Displays the current question's category, value, text and its answer choices.
     * @param gameEngine the {@link GameEngine} providing the selected question
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        Question q = gameEngine.getCurrentQuestion();

        System.out.println("\n" + q.getCategory() + " for " + q.getValue());
        System.out.println("Question: " + q.getQuestionText());

        String[] options = q.getOptions();
        String[] labels = { "A", "B", "C", "D" };
        for (int i = 0; i < options.length; i++) {
            System.out.println(labels[i] + ") " + options[i]);
        }

        /**
         * Logs the question being asked
         * 
         */
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(),
                "ASK_QUESTION",
                q.getCategory(),
                q.getValue(),
                null,
                null,
                gameEngine.getCurrentPlayer().getScore());
    }
    
    /**
     * Transitions to the accept answer state.
     * @param gameEngine the {@link GameEngine} managing the state transition
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new AcceptAnswerState());
    }
}
