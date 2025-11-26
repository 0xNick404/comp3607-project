package com.example.gameplay.state;

import com.example.model.Player;

public class UpdateScoreState implements GameState {
    @Override
    public void loadGameState(GameEngine gameEngine) {
        Player player = gameEngine.getCurrentPlayer();
        int points = gameEngine.getCurrentQuestion().getValue();

        if (gameEngine.isAnswerCorrect()) {
            player.updateScore(points);
        } else {
            player.updateScore(-points);
        }

        gameEngine.getCurrentQuestion().markPicked();

        System.out.println(player.getName() + " now has $" + player.getScore());

        // Log the score update (after the score has been updated)
        String result = gameEngine.isAnswerCorrect() ? "CORRECT" : "INCORRECT";
        gameEngine.publishEvent(
                player.getName(),
                "SCORE_UPDATED",
                gameEngine.getCurrentQuestion().getCategory(),
                gameEngine.getCurrentQuestion().getValue(),
                gameEngine.getInput(), // The answer that was given
                result,
                player.getScore() // score after update
        );

        gameEngine.updatePlayerTurn();
    }

    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new PlayerTurnState());
    }
}
