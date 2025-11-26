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

    /**
     * Constructor to initialise input handling, players, questions.
     * @param questions the list of questions that make up the current jeopardy game.
     */
    public GameEngine(List<Question> questions){
        this.scanner = new Scanner(System.in);
        this.players = PlayerInitFactory.createPlayers();
        this.currentPlayer = 0;
        this.questions = questions;
        this.game = new PlayerTurnState();
    }

    /**
     * Mutator Method for {@code private GameState game;}
     * Updates the current game state.
     * @param gameState the new {@link GameState} to delegate behavior to
     */
    public void setGameState(GameState gameState){
        this.game = gameState;
    }

    /**
     * Accessor Method for {@code private GameState game;}
     * Returns the active game state.
     * @return the current {@link GameState}
     */
    public GameState getGameState(){
        return this.game;
    }

    /**
     * Invokes the next logical state transition. The concrete state class determines
     * what the next state should be and updates the game engine accordingly.
     */
    public void nextGameState(){
        this.game.nextGameState(this);
    }

    /**
     * Returns the player whose turn it currently is.
     * @return the active {@link Player}
     */
    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    /**
     * Advances the turn to the next player in the sequence.
     * Wraps back to the first player when the end of the player list is reached.
     */
    public void updatePlayerTurn(){
        currentPlayer++;
        if(currentPlayer >= players.size()){
            currentPlayer = 0;
        }
    }

    /**
     * Accessor Method for {@code private List<Question> questions;}
     * Returns all questions stored in list.
     * @return the list of {@link Question} objects
     */
    public List<Question> getQuestions(){
        return this.questions;
    }

    /**
     * Determines whether every question in the list has been picked.
     * @return {@code true} if all questions have been answered, otherwise {@code false}
     */
    public boolean areAllQuestionsAnswered(){
        for(Question q : questions){
            if(!q.hasBeenPicked()){
                return false;
            }
        }

        return true;
    }

    /**
     * Searches for a question by category and point value.
     * @param category the question category to search for
     * @param value the point value to search for
     * @return the matching {@link Question}, or {@code null} if it is not found
     */
    public Question findQuestion(String category, int value){
        for(Question q : questions){
            if(q.getCategory().equalsIgnoreCase(category) && q.getValue() == value){
                return q;
            }
        }

        return null;
    }

    /**
     * Mutator Method for {@code private Question currentQuestion;}
     * Sets the current chosen question.
     * @param q the question selected by the player
     */
    public void setCurrentQuestion(Question q){
        this.currentQuestion = q;
    }

    /**
     * Accessor Method for {@code private Question currentQuestion;}
     * Returns the currently selected question.
     * @return the active {@link Question}
     */
    public Question getCurrentQuestion(){
        return this.currentQuestion;
    }

    /**
     * Reads input from the console for the active player.
     * @return the player's raw text input
     */
    public String getPlayerInput(){
        return scanner.nextLine();
    }

    /**
     * Mutator Method for {@code private String playerInput;}
     * @param input the String input from the player
     */
    public void setInput(String input){
        this.playerInput = input;
    }

    /**
     * Accessor Method for {@code private String playerInput;}
     * Returns the most recently stored player input.
     * @return the player's input string
     */
    public String getInput(){
        return this.playerInput;
    }

    /**
     * Mutator Method for {@code private boolean isAnswerCorrect;}
     * Records whether the player's answer to the current question was correct.
     * @param correct {@code true} if the answer was correct, otherwise {@code false}
     */
    public void setAnswerCorrect(boolean correct){
        this.isAnswerCorrect = correct;
    }

    /**
     * Returns whether the player's answer was correct.
     * @return {@code true} if the answer was correct, otherwise {@code false}
     */
    public boolean isAnswerCorrect(){
        return this.isAnswerCorrect;
    }

    /**
     * Accessor Method for {@code private List<Player> players;}
     * Returns the list of all players participating in the game.
     * @return a list of {@link Player} objects
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Indicates whether the game should continue running. The game is considered finished
     * when the current state is an instance of {@link GameOverState}.
     * @return {@code true} if the game is still active, {@code false} otherwise
     */
    public boolean isRunning() {
        if(this.game instanceof GameOverState)
            return false;
        else
            return true;
    }

    /**
     * 
     * @return
     */
    public List<GameTurn> getTurns(){
        return Collections.unmodifiableList(turns);
    }
    
    /**
     * helper method to log game events
     */
    public void publishEvent(String playerId,
            String activity,
            String category,
            Integer questionValue,
            String answerGiven,
            String result,
            Integer scoreAfterPlay) {
        if (publisher == null)
            return;
        LogHelper.publishEvent(publisher, caseId, playerId, activity, category, questionValue, answerGiven, result,
                scoreAfterPlay);
    }

    public EventPublisher getPublisher() {
        return publisher;
    }

    public String getCaseId() {
        return caseId;
    }
}

