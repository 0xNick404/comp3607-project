package com.example.gameplay.state;

/**
 * 
 */
public class AcceptAnswerState implements GameState{
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.print("\nWhat is ");
        String input = gameEngine.getPlayerInput();
        gameEngine.setInput(input);
    }
    
    @Override
    public void nextGameState(GameEngine gameEngine) {
        String input = gameEngine.getInput();

        if(input.equalsIgnoreCase("quit")){
            gameEngine.setGameState(new GameOverState());
        }
        else{
            gameEngine.setGameState(new CheckAnswerState());
        }
    }
}
