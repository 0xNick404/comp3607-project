package com.example.gameplay.state;

import com.example.model.Player;

/**
 * Concrete class for the final state that displays the final scoreboard, determines the winner and exits the program.
 * @author Nicholas Grimes
 */
public class GameOverState implements GameState{
    /**
     * Prints the game-over header, displays each player's final score, and
     * determines which player has the highest score to declare the winner.
     * @param gameEngine the {@link GameEngine} providing access to all players and scores
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\n--------------------------------");
        System.out.println("           GAME OVER");
        System.out.println("--------------------------------");

        int maxScore = Integer.MIN_VALUE;
        Player winner = null;

        System.out.println("Final Scores:");

        for (Player p : gameEngine.getPlayers()) {
            System.out.println(p.getName() + ": $" + p.getScore());

            if (p.getScore() > maxScore) {
                maxScore = p.getScore();
                winner = p;
            }
        }

        if (winner != null){
            System.out.println("\nThe Winner is: " + winner.getName() + "!\n");
        }

        // Log report generation
        gameEngine.publishEvent(
                null,
                "GENERATE_REPORT",
                null,
                null,
                null,
                null,
                null);

        // Log event log generation
        gameEngine.publishEvent(
                null,
                "GENERATE_EVENT_LOG",
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Displays a closing message and terminates the program.
     * @param gameEngine the {@link GameEngine} used to transition states
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        // Log game exit
        gameEngine.publishEvent(
                null,
                "EXIT_GAME",
                null,
                null,
                null,
                null,
                null);

        System.out.println("\nThank you for playing Jeopardy!");
        System.exit(0);
    }
}
