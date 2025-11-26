package com.example.gameplay.state;

/**
 * Concrete class that handles the beginning of a player's turn.
 * This state displays the current player's name and score, then determines whether gameplay
 * should continue or end based on if there are remaining questions.
 * @author Nicholas Grimes
 */
public class PlayerTurnState implements GameState{
    /**
     * Displays the active player's name and current score when the turn begins.
     * @param gameEngine the {@link GameEngine} providing player data
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\nIt is " + gameEngine.getCurrentPlayer().getName() + "'s turn");
        System.out.println("Score: $" + gameEngine.getCurrentPlayer().getScore());
    }

    /**
     * Moves the game to either the question-selection state or the game-over state,
     * depending on whether any unanswered questions remain.
     * @param gameEngine the {@link GameEngine} used to change the active game state
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        if(gameEngine.areAllQuestionsAnswered()){
            gameEngine.setGameState(new GameOverState());
        }
        else{
            gameEngine.setGameState(new SelectQuestionState());
        }
    }
}
