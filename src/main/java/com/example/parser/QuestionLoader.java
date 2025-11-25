package com.example.parser;

import com.example.parser.strategy.FileParserContext;
import com.example.model.Question;

import java.util.List;
import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;

/**
 * Class responsible for loading question data from a resource file determined by {@link FileParserContext}.
 * The class reads the file from the app's classpath and converts it into a list of {@link Question} objects
 * using the appropriate file parser strategy. The type of parser used (CSV, JSON or XML) is determined by
 * {@link FileParserContext} which evaluates the file extension and selects the matching {@link FileParserStrategy}
 * implementation.
 * @author Nicholas Grimes
 */
public class QuestionLoader{
    private final FileParserContext handler;

    /**
     * Constructor to initialise {@code FileParserContext handler}.
     */
    public QuestionLoader(){
        this.handler = new FileParserContext();
    }

    /**
     * Loads and parsers the game file specified by {@link FileParserContext}.
     * The file is retrieved from the app's classpath, parsed into a list of {@link Question} objects and returned.
     * If the file cannot be retrieved an empty list is returned.
     * @return a list of parsed {@link Question} objects or an empty list if the file could not be loaded or parsed
     */
    public List<Question> initQuestions(){
        String fileName = handler.getFileName();
        
        try {
            InputStream inputStream = QuestionLoader.class.getResourceAsStream("/" + fileName);

            if(inputStream == null){
                throw new IOException("Resource not found: " + fileName);
            }

            List<Question> questions = handler.loadQuestions(inputStream);
            inputStream.close();

            System.out.println("\nSuccessfully loaded " + questions.size() + " questions from " + fileName);
            return questions;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
