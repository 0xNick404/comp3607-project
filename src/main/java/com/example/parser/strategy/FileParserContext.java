package com.example.parser.strategy;

import com.example.parser.factory.RandomFileFactory;
import com.example.parser.factory.ParserFactory;
import com.example.model.Question;

import java.util.List;
import java.io.InputStream;

/**
 * Context class for selecting and executing the appropriate file parser strategy.
 * On creation, this context randomly selects a file name using {@link RandomFileFactory}
 * and uses {@link ParserFactory} to instantiate the correct {@link FileParserStrategy}
 * implementation based on the file extension.
 * @author Nicholas Grimes
 */
public class FileParserContext{
    private final String fileName;
    private final FileParserStrategy parser;

    /**
     * Constructs a new Context and determines the parser based on a randomly generated file name.
     */
    public FileParserContext(){
        this.fileName = RandomFileFactory.getRandomFileName();
        this.parser = ParserFactory.createParser(fileName);
    }

    /**
     * Loads and parses question data from the provided input stream using the selected parser strategy.
     * @param inputStream the data source to parse
     * @return a list of parsed {@link Question} objects
     */
    public List<Question> loadQuestions(InputStream inputStream){
        return parser.parse(inputStream);
    }

    /**
     * Returns the randomly selected file name associated with this context.
     * @return the file name used to determine the parsing strategy
     */
    public String getFileName(){return fileName;}
}
