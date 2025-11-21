package com.example.parser.strategy;

import java.io.InputStream;
import java.util.List;

import com.example.model.Question;
import com.example.parser.factory.ParserFactory;
import com.example.parser.factory.RandomFileFactory;

/**
 * Calls the execution method on the linked strategy object.
 */

public class FileParserContext{
    private final String fileName;
    private final FileParserStrategy parser;

    public FileParserContext(){
        this.fileName = RandomFileFactory.getRandomFileName();
        this.parser = ParserFactory.createParser(fileName);
    }

    public List<Question> loadQuestions(InputStream inputStream){
        return parser.parse(inputStream);
    }

    public String getFileName(){return fileName;}
}
