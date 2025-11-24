package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TXTReportGenerator implements ReportGenerator {

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
    
    // Report Content Writers
    private void writeReportContent(PrintWriter writer, GameEngine session) {
        writeHeader(writer);
        writeGameSummary(writer, session);
        writeFinalScores(writer, session.getPlayers());
        writeTurnByTurnHistory(writer, session.getTurns());
        writeFooter(writer);
    }
    
    // Header Section
    private void writeHeader(PrintWriter writer) {
        writer.println("=".repeat(80));
        writer.println("UWI COMP3607 JEOPARDY GAME - FINAL REPORT");
        writer.println("=".repeat(80));
        writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        writer.println();
    }
    
    private void writeGameSummary(PrintWriter writer, GameEngine session) {
        writer.println("--- GAME SUMMARY ---");
        writer.println("Total Players: " + session.getPlayers().size());
        writer.println("Total Turns: " + session.getTurns().size());
        writer.println("Game Started: " + session.getStartTime()); 
        writer.println("Game Ended: " + session.getEndTime()); 
        writer.println("--------------------\n");
    }
    
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
    
    // Footer Section
    private void writeFooter(PrintWriter writer) {
        writer.println("=".repeat(80));
        writer.println("           END OF REPORT - COMP3607 JEOPARDY");
        writer.println("=".repeat(80));
    }
    
    
    
    // Simple winner determination
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
    
    // Truncates long text, simple and safe
    private String truncateText(String text, int maxLength) {
        if (text == null) return "N/A";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }


}