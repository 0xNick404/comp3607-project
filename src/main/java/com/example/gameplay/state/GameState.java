package com.example.gameplay.state;

/**
 * This State Interface defines a common set of methods that represent the state-dependent behaviors.
 * All concrete state classes must implement this interface.
 * @author Nicholas Grimes
 */
public interface GameState{
    /**
     * Executes the logic that should occur when this game state becomes active.
     * @param gameEngine the GameEngine context controlling the game flow
     */
    public void loadGameState(GameEngine gameEngine);

    /**
     * Determines and sets the next appropriate state in the game after the current
     * state's actions have been completed.
     * @param gameEngine the GameEngine context controlling the game flow
     */
    public void nextGameState(GameEngine gameEngine);
}