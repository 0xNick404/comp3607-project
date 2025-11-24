package com.example.gameplay.state;

import com.example.model.Question;

/**
 * 
 */
public class AskQuestionState implements GameState{
    @Override
    public void loadGameState(GameEngine gameEngine) {
        Question q = gameEngine.getCurrentQuestion();

        System.out.println("\n" + q.getCategory() + " for " + q.getValue());
        System.out.println("Question: " + q.getQuestionText());

        String[] options = q.getOptions();
        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < options.length; i++) {
            System.out.println(labels[i] + ") " + options[i]);
        }
    }
    
    @Override
    public void nextGameState(GameEngine gameEngine) {  
        gameEngine.setGameState(new AcceptAnswerState());
    }
}
