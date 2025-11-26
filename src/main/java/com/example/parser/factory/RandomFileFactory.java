package com.example.parser.factory;

import java.util.Random;

/**
 * Factory class that provides randomly selected file names to be used for the game
 * @author Nicholas Grimes
 */
public class RandomFileFactory{
    private static String[] fileNames = { 
        "sample_game_CSV.csv", 
        "sample_game_JSON.json", 
        "sample_game_XML.xml" 
    };

    private static Random random = new Random();

    /**
     * Returns a random file name from the set of supported sample files.
     * @return a random file name
     */
    public static String getRandomFileName(){
        int i = random.nextInt(fileNames.length);
        return fileNames[i];
    }
}