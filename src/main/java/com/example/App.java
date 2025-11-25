package com.example;

import com.example.parser.QuestionLoader;
import com.example.parser.strategy.FileParserContext;
import com.example.model.Question;
import com.example.gameplay.state.GameEngine;

import java.io.InputStream;
import java.util.List;


/**
 * Main class to launch the Jeopardy application
 * @author 0xNick404
 */

public class App{
    /**
     * Main launching point
     * @param args
     */
    public static void main(String[] args){
        GameEngine engine = null;
        try{
            FileParserContext handler = new FileParserContext();
            InputStream inputStream = App.class.getResourceAsStream("/" + handler.getFileName());
            List<Question> questions = QuestionLoader.initQuestions(handler, handler.getFileName(), inputStream);

            System.out.println("\nWelcome to Jeopardy!\n");

            engine = new GameEngine(questions);

            while(engine.isRunning()){
                engine.getGameState().loadGameState(engine);
                engine.nextGameState();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(engine != null){
            ReportExecutor.executeReports(engine);
        }
    }

}
