package com.example;

import com.example.parser.QuestionLoader;
import com.example.model.Question;
import com.example.gameplay.state.GameEngine;
import com.example.logging.SimpleEventPublisher;
import com.example.logging.CSVEventLogger;
import com.example.logging.LogHelper;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

/**
 * Main class to launch the Jeopardy application
 * 
 * @author Nicholas Grimes
 * @author Shiann Noriega
 * @author Mahaveer Ragbir
 */
public class App{
    /**
     * Main launching point
     * @param args CLI arguments passed to the app
     */
    public static void main(String[] args) {
        try {
            // Create event publisher
            SimpleEventPublisher publisher = new SimpleEventPublisher();

            // Create CSV logger
            PrintWriter writer = new PrintWriter(new FileWriter("game_event_log.csv"));
            CSVEventLogger csvLogger = new CSVEventLogger(writer);

            // Register logger with publisher
            publisher.registerListener(csvLogger);

            // Generate unique case ID for this game session
            String caseId = "GAME-" + UUID.randomUUID().toString().substring(0, 8);

            // Log game start
            LogHelper.publishEvent(publisher, caseId, null, "START_GAME",
                    null, null, null, null, null);

            // Log file loading
            LogHelper.publishEvent(publisher, caseId, null, "LOAD_FILE",
                    null, null, null, null, null);

            QuestionLoader load = new QuestionLoader();
            List<Question> questions = load.initQuestions();

            // Log successful file load
            LogHelper.publishEvent(publisher, caseId, null, "FILE_LOADED_SUCCESSFULLY",
                    null, null, null, null, null);

            System.out.println("\nWelcome to Jeopardy!\n");

            // Create game engine with publisher and caseId
            GameEngine engine = new GameEngine(questions, publisher, caseId);

            while (engine.isRunning()) {
                engine.getGameState().loadGameState(engine);
                engine.nextGameState();
            }

            engine.getGameState().loadGameState(engine);
            ReportExecutor.executeReports(engine);

            // Close the writer
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
