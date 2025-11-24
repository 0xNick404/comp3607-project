package com.example.model;

public class Player{
    private int playerID;
    private String name;
    private int score = 0;

    public Player(int playerID, String name){
        this.playerID = playerID;
        this.name = name;
    }

    public int getPlayerID(){return this.playerID;}
    public String getName(){return this.name;}
    public int getScore(){return this.score;}

    public void updateScore(int value){
        score += value;
    }
}
