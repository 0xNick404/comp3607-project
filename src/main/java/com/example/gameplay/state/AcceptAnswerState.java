package com.example.gameplay.state;

/**
 * 
 */
public class AcceptAnswerState implements GameState {
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
