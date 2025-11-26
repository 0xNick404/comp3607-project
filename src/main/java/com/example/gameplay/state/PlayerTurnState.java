package com.example.gameplay.state;

/**
 * 
 */
public class PlayerTurnState implements GameState {
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\nIt is " + gameEngine.getCurrentPlayer().getName() + "'s turn");
        System.out.println("Score: $" + gameEngine.getCurrentPlayer().getScore());

        // Log player turn start
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(), 
                "PLAYER_TURN_START",
                null,
                null,
                null,
                null,
                gameEngine.getCurrentPlayer().getScore());
    }

    @Override
    public void nextGameState(GameEngine gameEngine) {
        if (gameEngine.areAllQuestionsAnswered()) {
            gameEngine.setGameState(new GameOverState());
        } else {
            gameEngine.setGameState(new SelectQuestionState());
        }
    }
}
