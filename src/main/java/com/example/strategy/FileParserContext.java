package com.example.strategy;

import java.io.InputStream;
import java.util.List;

import com.example.model.Question;

/**
 * Calls the execution method on the linked strategy object.
 */

public class FileParserContext{
    private final String fileName;

    public FileParserContext(String fileName){
        this.fileName = fileName;
    }

    public FileParserStrategy getFileParser(){
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

    public List<Question> loadQuestions(InputStream inputStream){
        FileParserStrategy parser = getFileParser();
        return parser.parse(inputStream);
    }
}
