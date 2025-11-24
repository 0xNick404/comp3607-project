package com.example.gameplay.state;

/**
 * 
 */
public interface GameState{
    public void loadGameState(GameEngine gameEngine);
    public void nextGameState(GameEngine gameEngine);
}