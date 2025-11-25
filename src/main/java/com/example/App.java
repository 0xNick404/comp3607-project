package com.example;

import com.example.parser.QuestionLoader;
import com.example.model.Question;
import com.example.gameplay.state.GameEngine;

import java.util.List;

/**
 * Main class to launch the Jeopardy application
 * @author Nicholas Grimes
 * @author Shiann Noriega
 */
public class App{
    /**
     * Main launching point
     * @param args CLI arguments passed to the app
     */
    public static void main(String[] args){
        try{
            QuestionLoader load = new QuestionLoader();
            List<Question> questions = load.initQuestions();

            System.out.println("\nWelcome to Jeopardy!\n");

            GameEngine engine = new GameEngine(questions);

            while(engine.isRunning()){
                engine.getGameState().loadGameState(engine);
                engine.nextGameState();
            }

            engine.getGameState().loadGameState(engine);
            ReportExecutor.executeReports(engine);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
