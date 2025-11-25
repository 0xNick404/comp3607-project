package com.example;

import com.example.parser.strategy.FileParserContext;
import com.example.model.Question;
import com.example.parser.QuestionLoader;
import com.example.gameplay.state.GameEngine;

import java.io.InputStream;
import java.util.List;


/**
 * Main class to launch the Jeopardy application
 * @author Nicholas Grimes
 */
public class App{
    /**
     * Main launching point
     * @param args CLI arguments passed to the app
     */
    public static void main(String[] args){
        try{
            FileParserContext handler = new FileParserContext();
            InputStream inputStream = App.class.getResourceAsStream("/" + handler.getFileName());
            List<Question> questions = QuestionLoader.initQuestions(handler, handler.getFileName(), inputStream);

            System.out.println("\nWelcome to Jeopardy!\n");

            GameEngine engine = new GameEngine(questions);

            while(engine.isRunning()){
                engine.getGameState().loadGameState(engine);
                engine.nextGameState();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
