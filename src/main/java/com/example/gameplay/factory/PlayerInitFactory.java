package com.example.gameplay.factory;

import com.example.model.Player;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Factory class responsible for initialising all players at the start of the game.
 * @author Nicholas Grimes
 */
public class PlayerInitFactory{
    private final static Scanner input = new Scanner(System.in);

    /**
     * Prompts the user for the number of players and their names, creating and returning a list of initialised {@link Player} objects.
     * @return a list of fully initialised players
     */
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
