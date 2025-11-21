package com.example.parser.factory;

import com.example.parser.strategy.FileParserStrategy;
import com.example.parser.strategy.CSVFileParser;
import com.example.parser.strategy.JSONFileParser;
import com.example.parser.strategy.XMLFileParser;

/**
 * 
 */

public class ParserFactory{
    public static FileParserStrategy createParser(String fileName){
        if(fileName.endsWith(".csv")){
            return new CSVFileParser();
        }
        else if(fileName.endsWith(".json")){
            return new JSONFileParser();
        }
        else if(fileName.endsWith(".xml")){
            return new XMLFileParser();
        }
        else{
            throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }
}
