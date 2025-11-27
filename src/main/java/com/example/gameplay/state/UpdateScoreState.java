package com.example.gameplay.state;

import com.example.model.GameTurn;
import com.example.model.Player;

/**
 * Concrete class to adjust the player's score based on whether their answer was correct and
 * marks the question as completed. After updating the score, this state advances the game to
 * the next player's turn.
 * @author Nicholas Grimes
 * @author Shiann Noriega
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

        boolean isCorrect = gameEngine.isAnswerCorrect();
        int pointsEarned = isCorrect ? points : -points;

        if (gameEngine.isAnswerCorrect()) {
            player.updateScore(points);
        } else {
            player.updateScore(-points);
        }

        gameEngine.getCurrentQuestion().markPicked();

        System.out.println(player.getName() + " now has $" + player.getScore());

        /**
         * Log the updated score (post updates)
         */
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

        GameTurn turn = new GameTurn(
            player, 
            gameEngine.getCurrentQuestion().getCategory(), 
            gameEngine.getCurrentQuestion().getValue(), 
            gameEngine.getCurrentQuestion().getQuestionText(), 
            gameEngine.getInput(), 
            isCorrect, 
            pointsEarned, 
            player.getScore()
        );

        gameEngine.recordTurn(turn);

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
