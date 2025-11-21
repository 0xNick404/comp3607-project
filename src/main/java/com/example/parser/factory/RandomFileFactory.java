package com.example.parser.factory;

import java.util.Random;

/**
 * 
 */

public class RandomFileFactory{
    private static String[] fileNames = { 
        "sample_game_CSV.csv", 
        "sample_game_JSON.json", 
        "sample_game_XML.xml" 
    };

    private static Random random = new Random();

    public static String getRandomFileName(){
        int i = random.nextInt(fileNames.length);
        return fileNames[i];
    }
}