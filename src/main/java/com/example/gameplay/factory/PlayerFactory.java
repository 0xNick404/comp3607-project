package com.example.gameplay.factory;

import com.example.model.Player;

/**
 * Class responsible for creating {@link Player} objects.
 * Follows the Factory Design Pattern.
 * @author Nicholas Grimes
 */
public class PlayerFactory{
    /**
     * Creates a new player with the provided name.
     * @param ID the unique identifier for the player
     * @param name the player's screen name
     * @return a new {@link Player} instance
     */
    public static Player createPlayer(int ID, String name){
        return new Player(ID, name);
    }
}
