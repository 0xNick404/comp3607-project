package com.example.parser;

import com.example.model.Question;
import com.example.parser.strategy.FileParserContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 
 */

public class QuestionLoader {
    public static List<Question> initQuestions(FileParserContext context, String fileName, InputStream inputStream){
        String file = fileName;
        InputStream input = inputStream;
        FileParserContext handler = context;
        List<Question> questions = null;
        
        try {
            questions = handler.loadQuestions(input);
            inputStream.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nSuccessfully loaded " + questions.size() + " questions from " + file);
        return questions;
    }
}
