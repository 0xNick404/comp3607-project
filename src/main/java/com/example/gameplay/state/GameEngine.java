package com.example.gameplay.state;

import com.example.gameplay.factory.PlayerInitFactory;
import com.example.model.Player;
import com.example.model.Question;
import com.example.model.GameTurn;
import com.example.logging.EventPublisher;
import com.example.logging.LogHelper;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The controller for the game and Context Class in the State Design Pattern.
 * The {@link GameEngine} manages the flow of gameplay by delegating behavior to the current {@link GameState}.
 * @author Nicholas Grimes
 * @author Shiann Noriega
 * @author Mahaveer Ragbir
 */
public class GameEngine {
    private GameState game;
    private Scanner scanner;
    private List<Player> players;
    private List<Question> questions;
    private List<GameTurn> turns = new ArrayList<>();
    private int currentPlayer;
    private Question currentQuestion;
    private String playerInput;
    private boolean isAnswerCorrect;
    private EventPublisher publisher;  // ← ADD THIS
    private String caseId;              // ← ADD THIS

    /**
     * Constructor to initialise input handling, players, questions.
     * @param questions the list of questions that make up the current jeopardy game.
     * @param publisher the event publisher for logging
     * @param caseId the unique case ID for this game session
     */
    public GameEngine(List<Question> questions, EventPublisher publisher, String caseId){
        this.scanner = new Scanner(System.in);
        this.players = PlayerInitFactory.createPlayers();
        this.currentPlayer = 0;
        this.questions = questions;
        this.publisher = publisher;     // ← ADD THIS
        this.caseId = caseId;           // ← ADD THIS
        this.game = new PlayerTurnState();
    }
    
    // ... rest of the code stays the same
