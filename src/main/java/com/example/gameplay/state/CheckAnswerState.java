package com.example.gameplay.state;

/**
 * 
 */
public class CheckAnswerState implements GameState {
    @Override
    public void loadGameState(GameEngine gameEngine) {
        String playerInput = gameEngine.getInput();
        String correctAnswer = gameEngine.getCurrentQuestion().getCorrectAnswer();

        if (playerInput.equalsIgnoreCase(correctAnswer)) {
            System.out.println("\nCORRECT!");
            gameEngine.setAnswerCorrect(true);
        } else {
            System.out.println("\nWRONG! The correct answer was: " + correctAnswer);
            gameEngine.setAnswerCorrect(false);
        }

        /**
         * Logs the answer check result
         * 
         */
        String result = gameEngine.isAnswerCorrect() ? "CORRECT" : "INCORRECT";
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(),
                "CHECK_ANSWER",
                gameEngine.getCurrentQuestion().getCategory(),
                gameEngine.getCurrentQuestion().getValue(),
                playerInput,
                result,
                gameEngine.getCurrentPlayer().getScore());
    }

    @Override
    public void nextGameState(GameEngine gameEngine) {
        gameEngine.setGameState(new UpdateScoreState());
    }
}
