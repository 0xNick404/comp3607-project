package com.example.gameplay.state;

import com.example.model.Player;

/**
 * 
 */
public class GameOverState implements GameState {
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\n--------------------------------");
        System.out.println("       GAME OVER");
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

        if (winner != null) {
            System.out.println("\nThe Winner is: " + winner.getName() + "!");
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
