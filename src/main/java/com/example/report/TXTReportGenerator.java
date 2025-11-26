/**
 * Generates a plain-text (.txt) report summarizing the final results
 * of a Jeopardy-style game session. The report includes:
 * <ul>
 *     <li>Game metadata (start time, end time, number of players, etc.)</li>
 *     <li>Final scores and winner</li>
 *     <li>Turn-by-turn gameplay history</li>
 * </ul>
 *
 * This class implements the {@link ReportGenerator} interface and
 * writes all report content to a user-specified file or a default name
 * if none is provided.
 */

package com.example.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.gameplay.state.GameEngine;
import com.example.model.Player;
import com.example.model.GameTurn;



public class TXTReportGenerator implements ReportGenerator {

    /**
     * Generates a complete TXT report for the given game session.
     *
     * <p>If {@code filePath} is null or blank, a default timestamped filename
     * will be created. If the provided filename does not end with
     * {@code ".txt"}, the extension is automatically appended.</p>
     *
     * @param session  The active {@link GameEngine} session containing game results.
     * @param filePath The desired output path for the report. May be null or blank.
     * @throws ReportGenerationException If any I/O error occurs while writing the file.
     */
    
    @Override
    public void generateReport(GameEngine session, String filePath) throws ReportGenerationException {
        // Set default filename if none provided
        if (filePath == null || filePath.trim().isEmpty()) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            filePath = "jeopardy_report_" + timestamp + ".txt";
        }
        
        // Ensure .txt extension is present
        else if (!filePath.toLowerCase().endsWith(".txt")) {
            filePath += ".txt";
        }
        
        // Using try-with-resources to safely handle the file writer
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writeReportContent(writer, session);
            System.out.println("TXT report generated successfully: " + filePath);
            
        } catch (IOException e) {
            // Throw custom exception on failure
            throw new ReportGenerationException("Failed to write TXT report to: " + filePath, e);
        }
    }
    
     /**
     * Writes all components of the report to the provided {@link PrintWriter}.
     *
     * @param writer  The writer used to output the report.
     * @param session The game session used to construct report details.
     */
    private void writeReportContent(PrintWriter writer, GameEngine session) {
        writeHeader(writer);
        writeGameSummary(writer, session);
        writeFinalScores(writer, session.getPlayers());
        writeTurnByTurnHistory(writer, session.getTurns());
        writeFooter(writer);
    }
    
    /**
     * Writes the formatted header section of the report.
     *
     * @param writer The writer receiving the header content.
     */
    private void writeHeader(PrintWriter writer) {
        writer.println("=".repeat(80));
        writer.println("UWI COMP3607 JEOPARDY GAME - FINAL REPORT");
        writer.println("=".repeat(80));
        writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        writer.println();
    }

    /**
     * Writes a brief summary of the game session including player count,
     * total turns, and timestamps for game start and end.
     *
     * @param writer  The writer receiving the summary content.
     * @param session The session containing game metadata.
     */
    
    private void writeGameSummary(PrintWriter writer, GameEngine session) {
        writer.println("--- GAME SUMMARY ---");
        writer.println("Total Players: " + session.getPlayers().size());
        writer.println("Total Turns: " + session.getTurns().size());

       
        writer.println("--------------------\n");
    }

    /**
     * Writes each player's final score and highlights the winner if applicable.
     *
     * @param writer  The writer receiving the score content.
     * @param players The list of participating players.
     */
    
    private void writeFinalScores(PrintWriter writer, List<Player> players) {
        writer.println("--- FINAL SCORES ---");
        
        Player winner = findWinner(players);
        boolean hasWinner = winner != null && winner.getScore() > 0;
        
        // Scores Listing
        for (Player player : players) {
            String winnerIndicator = "";
            if (hasWinner && player.equals(winner)) {
                winnerIndicator = " *** WINNER! ***";
            }
            // Simple formatted output
            writer.printf("%-15s: %+d points%s%n", 
                          player.getName(), 
                          player.getScore(), 
                          winnerIndicator);
        }
        
        if (hasWinner) {
            writer.println("\nCongratulations to " + winner.getName() + "!");
        }
        writer.println("--------------------\n");
    }

    /**
     * Writes a structured table summarizing every turn of the game.
     *
     * @param writer The writer receiving the turn-by-turn data.
     * @param turns  The list of all game turns in order.
     */

    private void writeTurnByTurnHistory(PrintWriter writer, List<GameTurn> turns) {
        writer.println("--- TURN-BY-TURN HISTORY ---");
        
        // Simplified Table Header using fixed strings
        String header = String.format("%-5s | %-12s | %-15s | %-8s | %-25s | %-10s | %-12s",
                      "#", "PLAYER", "CATEGORY", "VALUE", "ANSWER", "RESULT", "TOTAL");
        writer.println(header);
        writer.println("-".repeat(92)); // Simple separator line
        
        // Table Rows
        for (int i = 0; i < turns.size(); i++) {
            GameTurn turn = turns.get(i);
            writeTurnRow(writer, turn, i + 1);
        }
        writer.println("--------------------------\n");
    }

    /**
     * Writes the details of a single game turn including:
     * <ul>
     *     <li>Player name</li>
     *     <li>Category and question value</li>
     *     <li>Answer (truncated)</li>
     *     <li>Correctness</li>
     *     <li>Running total</li>
     * </ul>
     *
     * @param writer     The writer receiving the row content.
     * @param turn       The turn being printed.
     * @param turnNumber The sequential turn number.
     */
    private void writeTurnRow(PrintWriter writer, GameTurn turn, int turnNumber) {
        // Truncate long answers for neatness
        String answer = truncateText(turn.getGivenAnswer(), 25);
        String result;
        if (turn.isCorrect()) {
            result = "CORRECT";
        } else {
            result = "WRONG";
        }
        
        // Simple formatted output
        writer.printf("%-5d | %-12s | %-15s | $%-7d | %-25s | %-10s | %-12d%n",
                      turnNumber,
                      turn.getPlayer().getName(),
                      turn.getCategory(),
                      turn.getQuestionValue(),
                      answer,
                      result,
                      turn.getRunningTotal());
        // Optional detailed question info
        writer.println("      Question: " + turn.getQuestionText());
        writer.println();
    }
    
   /**
     * Writes the footer section indicating the end of the report.
     *
     * @param writer The writer receiving the footer content.
     */
    private void writeFooter(PrintWriter writer) {
        writer.println("=".repeat(80));
        writer.println("           END OF REPORT - COMP3607 JEOPARDY");
        writer.println("=".repeat(80));
    }
    
    
    
    /**
     * Identifies the player with the highest score.
     *
     * @param players The list of players to evaluate.
     * @return The player with the highest score, or {@code null} if the list is empty.
     */
    private Player findWinner(List<Player> players) {
        if (players == null || players.isEmpty()) {
            return null;
        }
        Player winner = players.get(0);
        for (Player p : players) {
            if (p.getScore() > winner.getScore()) {
                winner = p;
            }
        }
        return winner;
    }
    
     /**
     * Truncates a string to a maximum length, appending "..." if truncated.
     *
     * @param text      The text to truncate.
     * @param maxLength The maximum allowed length.
     * @return The truncated text, or "N/A" if the input is null.
     */
    private String truncateText(String text, int maxLength) {
        if (text == null) return "N/A";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }



}