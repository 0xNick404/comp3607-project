package com.example.gameplay.state;

import java.util.List;

import com.example.model.Question;

/**
 * 
 */
public class SelectQuestionState implements GameState {
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\n--- Available Questions ---");
        List<Question> questions = gameEngine.getQuestions();

        for (Question q : questions) {
            if (!q.hasBeenPicked()) {
                System.out.println("[" + q.getCategory() + " " + q.getValue() + "]");
            }
        }

        System.out.println("\nType 'quit' to end the game\n");
        System.out.print("Choose a Category and Value (e.g., 'Variables & Data Types,200'): ");
    }

    @Override
    public void nextGameState(GameEngine gameEngine) {
        String input = gameEngine.getPlayerInput();

        if (input.equalsIgnoreCase("quit")) {
            gameEngine.setGameState(new GameOverState());
            return;
        }

        String[] parts = input.split(",");
        if (parts.length < 2) {
            System.out.println("\nInvalid format. Please try again.");
            gameEngine.setGameState(new SelectQuestionState());
            return;
        }

        String category = parts[0].trim(); // Added trim() to remove extra spaces
        int value = 0;

        try {
            value = Integer.parseInt(parts[1].trim()); // Added trim() here too
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
            gameEngine.setGameState(new SelectQuestionState());
            return;
        }

        // Log category selection
        gameEngine.publishEvent(
                gameEngine.getCurrentPlayer().getName(),
                "SELECT_CATEGORY",
                category,
                null,
                null,
                null,
                gameEngine.getCurrentPlayer().getScore());

        Question selected = gameEngine.findQuestion(category, value);

        if (selected != null && !selected.hasBeenPicked()) {
            // Log question selection
            gameEngine.publishEvent(
                    gameEngine.getCurrentPlayer().getName(),
                    "SELECT_QUESTION",
                    category,
                    value,
                    null,
                    null,
                    gameEngine.getCurrentPlayer().getScore());

            gameEngine.setCurrentQuestion(selected);
            gameEngine.setGameState(new AskQuestionState());
        } else {
            System.out.println("\nQuestion not found or already played.");
            gameEngine.setGameState(new SelectQuestionState());
        }
    }
}
