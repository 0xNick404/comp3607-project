package com.example.gameplay.state;

/**
 * Concrete class retrieves the player's input and determines the next state based on the input.
 * If the user enters "quit", the game transitions to the game over state.
 * @author Nicholas Grimes
 */
public class AcceptAnswerState implements GameState{
    /**
     * Prompts and captures the player's submitted answer. The retrieved input is stored in the 
     * {@link GameEngine} for later validation.
     * @param gameEngine the {@link GameEngine} used to read and store player input
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.print("\nWhat is your answer? ");
        String input = gameEngine.getPlayerInput();
        gameEngine.setInput(input);

        /**
         * Logs the player's answer submission event with game details
         * including category, question value, and current score
         * 
         */
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(),
                "ANSWER_QUESTION",
                gameEngine.getCurrentQuestion().getCategory(),
                gameEngine.getCurrentQuestion().getValue(),
                input, // This is the player's answer
                null,
                gameEngine.getCurrentPlayer().getScore());
    }
    
    /**
     * Determines whether the player wants to quit or proceed to the answer checking state.
     * If the input is "quit", the game ends. Otherwise, the game continues to check the answer.
     * @param gameEngine the {@link GameEngine} used to transition states
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        String input = gameEngine.getInput();

        if (input.equalsIgnoreCase("quit")) {
            gameEngine.setGameState(new GameOverState());
        } else {
            gameEngine.setGameState(new CheckAnswerState());
        }
    }
}
