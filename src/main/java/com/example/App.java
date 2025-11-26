package com.example;

import com.example.parser.QuestionLoader;
import com.example.model.Question;
import com.example.gameplay.state.GameEngine;
import com.example.logging.SimpleEventPublisher;
import com.example.logging.CSVEventLogger;
import com.example.logging.LogHelper;

import java.util.List;
import java.util.UUID;

/**
 * Main class to launch the Jeopardy application.
 * Initializes the game, loads questions, sets up logging, and runs the game engine.
 * 
 * Logging is handled using a CSVEventLogger registered with a SimpleEventPublisher.
 * Game events are published at key points, such as game start, file loading, and game state changes.
 * 
 * @author Nicholas Grimes
 * @author Shiann Noriega
 * @author Mahaveer Ragbir
 */
public class App {

    /**
     * Main launching point of the Jeopardy application.
     * Initializes the event publisher, logger, and game engine, then runs the game loop.
     *
     * @param args CLI arguments passed to the application
     */
    public static void main(String[] args) {
        try {
            /** 
             * Initializes the event publisher for logging game events.
             */
            SimpleEventPublisher publisher = new SimpleEventPublisher();

            /**
             * Creates a CSV logger to record all events to "game_event_log.csv".
             * This logger is responsible for writing headers and appending new event records.
             */
            CSVEventLogger csvLogger = new CSVEventLogger("game_event_log.csv");

            /**
             * Registers the CSV logger with the publisher so that all published events are logged.
             */
            publisher.registerListener(csvLogger);

            /**
             * Generates a unique case ID for this game session.
             * This ID is used to track all events for this specific playthrough.
             */
            String caseId = "GAME-" + UUID.randomUUID().toString().substring(0, 8);

            /**
             * Publishes a START_GAME event to indicate the game session has begun.
             */
            LogHelper.publishEvent(publisher, caseId, null, "START_GAME",
                    null, null, null, null, null);

            /**
             * Publishes a LOAD_FILE event to indicate that the question file is being loaded.
             */
            LogHelper.publishEvent(publisher, caseId, null, "LOAD_FILE",
                    null, null, null, null, null);

            QuestionLoader load = new QuestionLoader();
            List<Question> questions = load.initQuestions();

            /**
             * Publishes FILE_LOADED_SUCCESSFULLY event to indicate that questions were loaded successfully.
             */
            LogHelper.publishEvent(publisher, caseId, null, "FILE_LOADED_SUCCESSFULLY",
                    null, null, null, null, null);

            System.out.println("\nWelcome to Jeopardy!\n");

            /**
             * Creates the game engine with loaded questions, publisher, and case ID.
             * The engine controls the flow of game states and gameplay.
             */
            GameEngine engine = new GameEngine(questions, publisher, caseId);

            /**
             * Runs the game loop until the engine is no longer running.
             * Each game state is loaded and transitioned in sequence.
             */
            while (engine.isRunning()) {
                engine.getGameState().loadGameState(engine);
                engine.nextGameState();
            }

            engine.getGameState().loadGameState(engine);

            /**
             * Executes any final reports at the end of the game.
             */
            ReportExecutor.executeReports(engine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
