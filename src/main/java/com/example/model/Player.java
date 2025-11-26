package com.example.model;

/**
 * Represents a Player with ID, Name and Score.
 * Updates players' scores.
 * @author Nicholas Grimes
 */
public class Player{
    private int playerID;
    private String name;
    private int score = 0;

    /**
     * Constructor to initialise {@code int playerID} and {@code String name}
     * @param playerID the player's unique identification
     * @param name the player's screen name
     */
    public Player(int playerID, String name){
        this.playerID = playerID;
        this.name = name;
    }

    /**
     * Accessor Method for {@code private int playerID;}
     * @return the player's ID
     */
    public int getPlayerID(){return this.playerID;}

    /**
     * Accessor Method for {@code private String name;}
     * @return the player's name
     */
    public String getName(){return this.name;}

    /**
     * Accessor Method for {@code private int score;}
     * @return the player's score
     */
    public int getScore(){return this.score;}

    /**
     * Adds the question value to the player's score
     * @param value the amount to add to the player's current score
     */
    public void updateScore(int value){
        score += value;
    }
}
