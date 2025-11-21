package com.example;

import com.example.model.Question;
import com.example.parser.strategy.FileParserContext;

import java.io.InputStream;
import java.util.List;


/**
 * 
 */
public class App{
    public static void main(String[] args){
        try{
            FileParserContext handler = new FileParserContext();
            String fileName = handler.getFileName();
            InputStream inputStream = App.class.getResourceAsStream("/" + fileName);
            List<Question> questions = handler.loadQuestions(inputStream);

            System.out.println("Successfully loaded " + questions.size() + " questions from " + fileName);

            inputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
