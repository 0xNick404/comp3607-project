package com.example.gameplay.state;

/**
 * Concrete class validates the player's submitted answer against the correct answer for the current question.
 * Displays whether the player was correct or not and stores the result for later score calculation.
 * @author Nicholas Grimes
 */
public class CheckAnswerState implements GameState{
    /**
     * Compares the player's input with the correct answer. Indicates whether the answer was correct by displaying
     * the appropriate feedback.
     * @param gameEngine the {@link GameEngine} containing the player's answer and question data
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        String playerInput = gameEngine.getInput();
        String correctAnswer = gameEngine.getCurrentQuestion().getCorrectAnswer();

        if (playerInput.equalsIgnoreCase(correctAnswer)) {
            System.out.println("\nCORRECT!");
            gameEngine.setAnswerCorrect(true);
        } else {
            System.out.println("\nWRONG! The correct answer was: " + correctAnswer);
            gameEngine.setAnswerCorrect(false);
        }

        /**
         * Logs the answer check result
         * 
         */
        String result = gameEngine.isAnswerCorrect() ? "CORRECT" : "INCORRECT";
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(),
                "CHECK_ANSWER",
                gameEngine.getCurrentQuestion().getCategory(),
                gameEngine.getCurrentQuestion().getValue(),
                playerInput,
                result,
                gameEngine.getCurrentPlayer().getScore());
    }

    /**
     * Transitions to the {@link UpdateScoreState} to compute point changes based on the correctness of the player's answer.
     * @param gameEngine the {@link GameEngine} used to transition states
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new UpdateScoreState());
    }
}
