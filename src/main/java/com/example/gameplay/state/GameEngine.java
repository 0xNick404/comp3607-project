package com.example.gameplay.state;

import com.example.gameplay.factory.PlayerInitFactory;
import com.example.model.Player;
import com.example.model.Question;

import java.util.List;
import java.util.Scanner;

/**
 * Context
 */

public class GameEngine {
    private GameState game;
    private Scanner scanner;
    private List<Player> players;
    private List<Question> questions;
    private int currentPlayer;
    private Question currentQuestion;
    private String playerInput;
    private boolean isAnswerCorrect;

    public GameEngine(List<Question> questions){
        this.scanner = new Scanner(System.in);
        this.players = PlayerInitFactory.createPlayers();
        this.currentPlayer = 0;
        this.questions = questions;
        this.game = new PlayerTurnState();
    }

    public void setGameState(GameState gameState){
        this.game = gameState;
    }

    public GameState getGameState(){
        return this.game;
    }

    public void nextGameState(){
        this.game.nextGameState(this);
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public void updatePlayerTurn(){
        currentPlayer++;
        if(currentPlayer >= players.size()){
            currentPlayer = 0;
        }
    }

    public List<Question> getQuestions(){
        return this.questions;
    }

    public boolean areAllQuestionsAnswered(){
        for(Question q : questions){
            if(!q.hasBeenPicked()){
                return false;
            }
        }

        return true;
    }

    public Question findQuestion(String category, int value){
        for(Question q : questions){
            if(q.getCategory().equalsIgnoreCase(category) && q.getValue() == value){
                return q;
            }
        }

        return null;
    }

    public void setCurrentQuestion(Question q){
        this.currentQuestion = q;
    }

    public Question getCurrentQuestion(){
        return this.currentQuestion;
    }

    public String getPlayerInput(){
        return scanner.nextLine();
    }

    public void setInput(String input){
        this.playerInput = input;
    }

    public String getInput(){
        return this.playerInput;
    }

    public void setAnswerCorrect(boolean correct){
        this.isAnswerCorrect = correct;
    }

    public boolean isAnswerCorrect(){
        return this.isAnswerCorrect;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public boolean isRunning() {
        if(this.game instanceof GameOverState)
            return false;
        else
            return true;
    }
}
