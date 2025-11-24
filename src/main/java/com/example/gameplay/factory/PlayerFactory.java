package com.example.gameplay.factory;

import com.example.model.Player;

/**
 * 
 */

public class PlayerFactory{
    public static Player createPlayer(int ID, String name){
        return new Player(ID, name);
    }
}
