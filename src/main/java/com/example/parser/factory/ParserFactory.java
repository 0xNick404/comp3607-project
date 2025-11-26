package com.example.parser.factory;

import com.example.parser.strategy.FileParserStrategy;
import com.example.parser.strategy.CSVFileParser;
import com.example.parser.strategy.JSONFileParser;
import com.example.parser.strategy.XMLFileParser;

/**
 * Factory class responsible for creating the appropriate {@link FileParserStrategy} implementation based on the file name.
 * Supports CSV, JSON, and XML file formats.
 * @author Nicholas Grimes
 */
public class ParserFactory{
    /**
     * Creates a parser strategy based on the file's extension.
     * @param fileName the name of the file whose format needs to be parsed
     * @return the corresponding {@link FileParserStrategy}
     */
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
