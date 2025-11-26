package com.example.gameplay.state;

import com.example.model.Question;

import java.util.List;

/**
 * Concrete class displays a list of all available questions and waits for the player's choice.
 * The state validates the player's input and either advances to the question asking phase or 
 * re-prompts the player in case of invalid input.
 * @author Nicholas Grimes
 */
public class SelectQuestionState implements GameState{
    /**
     * Displays all categories and values for questions that have not yet been picked and prompts the player to make a selection.
     * @param gameEngine the {@link GameEngine} providing question data
     */
    @Override
    public void loadGameState(GameEngine gameEngine) {
        System.out.println("\n--- Available Questions ---");
        List<Question> questions = gameEngine.getQuestions();
        
        for(Question q : questions){
            if(!q.hasBeenPicked()){
                System.out.println("[" + q.getCategory() + " " + q.getValue() + "]");
            }
        }

        System.out.println("\nType 'quit' to end the game\n");
        System.out.print("Choose a Category and Value (e.g., 'Variables & Data Types,200'): ");
    }

    /**
     * Validates the player's input and transitions to the next game state accordingly.
     * If the player types "quit", the game ends.
     * If the input format is invalid, the state restarts.
     * If a matching question is found, the game moves to the ask question state.
     * @param gameEngine the {@link GameEngine} used to read input and change states
     */
    @Override
    public void nextGameState(GameEngine gameEngine) {
        String input = gameEngine.getPlayerInput();

        if(input.equalsIgnoreCase("quit")){
            gameEngine.setGameState(new GameOverState());
            return;
        }

        String[] parts = input.split(",");
        if(parts.length < 2){
            System.out.println("\nInvalid format. Please try again.");
            gameEngine.setGameState(new SelectQuestionState()); 
            return;
        }

        String category = parts[0];
        int value = 0;

        try{
            value = Integer.parseInt(parts[1]);
        }
        catch(NumberFormatException e){
            System.out.println("Invalid number. Please try again.");
            gameEngine.setGameState(new SelectQuestionState());
            return;
        }

        Question selected = gameEngine.findQuestion(category, value);

        if(selected != null && !selected.hasBeenPicked()){
            gameEngine.setCurrentQuestion(selected);
            gameEngine.setGameState(new AskQuestionState());
        }
        else{
            System.out.println("\nQuestion not found or already played.");
            gameEngine.setGameState(new SelectQuestionState());
        }
    }
}
