package com.example.gameplay.state;

import com.example.model.Player;

public class UpdateScoreState implements GameState{
    @Override
    public void loadGameState(GameEngine gameEngine) {
        Player player = gameEngine.getCurrentPlayer();
        int points = gameEngine.getCurrentQuestion().getValue();

        if(gameEngine.isAnswerCorrect()){
            player.updateScore(points);
        }
        else{
            player.updateScore(-points);
        }

        gameEngine.getCurrentQuestion().markPicked();

        System.out.println(player.getName() + " now has $" + player.getScore());

        gameEngine.updatePlayerTurn();
    }
    
    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new PlayerTurnState());
    }
}
