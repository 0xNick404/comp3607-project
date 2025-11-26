package com.example.gameplay.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.model.Player;

/**
 * 
 */

public class PlayerInitFactory{
    private final static Scanner input = new Scanner(System.in);

    public static List<Player> createPlayers(){
        List<Player> players = new ArrayList<>();

        System.out.print("Enter number of players for this game (1-4): ");
        int numPlayers = Integer.parseInt(input.nextLine());
        
        for(int i = 1; i <= numPlayers; i++){
            System.out.print("Enter name for Player " + i + ": ");
            String name = input.nextLine();

            Player p = PlayerFactory.createPlayer(i, name);
            players.add(p);
        }

        return players;
    }
}
