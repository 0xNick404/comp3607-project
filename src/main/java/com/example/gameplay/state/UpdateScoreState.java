package com.example.gameplay.state;

import com.example.model.Player;

/**
 * Concrete class to adjust the player's score based on whether their answer was correct and
 * marks the question as completed. After updating the score, this state advances the game to
 * the next player's turn.
 * @author Nicholas Grimes
 */
public class UpdateScoreState implements GameState{
    /**
     * Updates the player's score by adding or subtracting the question's value depending on correctness.
     * The current question is then marked as picked and the {@link GameEngine} advances to the next player.
     * @param gameEngine the {@link GameEngine} providing question data and player turn management
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        Player player = gameEngine.getCurrentPlayer();
        int points = gameEngine.getCurrentQuestion().getValue();

        if(gameEngine.isAnswerCorrect()){
            player.updateScore(points);
        }
        else{
            player.updateScore(-points);
        }

        gameEngine.getCurrentQuestion().markPicked();

        System.out.println(player.getName() + " now has $" + player.getScore());

        gameEngine.updatePlayerTurn();
    }
    
    /**
     * Returns the game to the PlayerTurnState so that the next player can begin their round.
     * @param gameEngine the {@link GameEngine} used to transition states
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new PlayerTurnState());
    }
}
