/**
 * Generates a DOCX-formatted report summarizing the results of a Jeopardy game.
 * This class uses Apache POI to create a Microsoft Word document (.docx)
 * containing:
 *
 * <ul>
 *   <li>A header with title and generation timestamp</li>
 *   <li>A table showing ranked final scores of all players</li>
 *   <li>A detailed turn-by-turn history of gameplay</li>
 * </ul>
 *
 * <p>If the provided file path is missing or empty, a timestamp-based filename
 * is used automatically. The file extension is ensured to be <code>.docx</code>.</p>
 */

package com.example;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.example.gameplay.state.GameEngine;
import com.example.model.Player;
import com.example.ReportGenerationException;
import com.example.model.GameTurn;

public class DocxReportGenerator implements ReportGenerator {

    /**
     * Generates a complete DOCX report for a Jeopardy game session.
     *
     * @param session  the active game session containing player scores and turn data
     * @param filePath output path for the DOCX file; a timestamped filename is used if empty
     * @throws ReportGenerationException if writing to disk or constructing the DOCX fails
     */

    @Override
    public void generateReport(GameEngine session, String filePath) throws ReportGenerationException {
        // Set default filename if none provided
        if (filePath == null || filePath.trim().isEmpty()) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            filePath = "jeopardy_report_" + timestamp + ".docx";
        } else if (!filePath.toLowerCase().endsWith(".docx")) {
            filePath += ".docx";
        }

        // Using try-with-resources to safely handle the document
        try (XWPFDocument document = new XWPFDocument()) {
            FileOutputStream out = new FileOutputStream(filePath);

            // Writing content to the DOCX document
            writeHeader(document);
            writeFinalScores(document, session.getPlayers());
            writeTurnByTurnHistory(document, session.getTurns());

            // Save the document to the file system
                document.write(out);
            
            System.out.println("DOCX report generated successfully: " + filePath);
        } catch (IOException e) {
            throw new ReportGenerationException("Failed to write DOCX report to: " + filePath, e);
        }
    }
/**
     * Writes the report title and timestamp at the top of the document.
     *
     * @param document the {@link XWPFDocument} being constructed
     */
    
    private void writeHeader(XWPFDocument document){
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("UWI COMP3607 JEOPARDY GAME - FINAL REPORT");
        titleRun.setBold(true);
        titleRun.setFontSize(20);

        XWPFParagraph date = document.createParagraph();
        date.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateRun = date.createRun();
        dateRun.setText("Generated: " + LocalDateTime.now());
        dateRun.setFontSize(12);

    }
    
/**
     * Writes a table listing players ranked by score, marking the winner if applicable.
     *
     * @param document the DOCX document
     * @param players  the list of players to be ranked
     */
    private void writeFinalScores(XWPFDocument document, List<Player> players){
       
        XWPFParagraph title =  document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("FINAL SCORES");
        titleRun.setBold(true);

        XWPFTable table = document.createTable(1, 3);
        table.removeRow(0); // Remove the default row
        XWPFTableRow headerRow = table.createRow();

        headerRow.getCell(0).setText("RANK");
        headerRow.getCell(1).setText("PLAYER NAME");
        headerRow.getCell(2).setText("SCORE");

        Optional<Player> winner = findWinner(players);
        players.sort(Comparator.comparingInt(Player::getScore).reversed());

        for(int i =0; i<players.size(); i++){
            Player p = players.get(i);
            XWPFTableRow row = table.createRow();

            row.getCell(0).setText(String.valueOf(i+1));

            String name = p.getName();
            if(winner.isPresent() && p.equals(winner.get()) && p.getScore() >0){
                name += " *** WINNER! ***";
            }
            row.getCell(1).setText(name);
            row.getCell(2).setText(String.valueOf(p.getScore()));
        }

        document.createParagraph(); // Add spacing after the table

    }

    /**
     * Finds the player with the highest score.
     *
     * @param players list of players
     * @return an {@link Optional} containing the highest-scoring player
     */
    
    private Optional<Player> findWinner(List<Player> players){
        return players.stream()
                .max(Comparator.comparingInt(Player::getScore));
    }
/**
     * Writes a detailed turn-by-turn table showing gameplay actions.
     *
     * @param document the DOCX document
     * @param turns    list of game turns from the session
     */
    
    private void writeTurnByTurnHistory(XWPFDocument document, List<GameTurn> turns){
         XWPFParagraph title =  document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("TURN BY TURN HISTORY");
        titleRun.setBold(true);

        XWPFTable table = document.createTable(1, 6);
        table.removeRow(0); // Remove the default row
        XWPFTableRow headerRow = table.createRow();

        headerRow.getCell(0).setText("TURN");
        headerRow.getCell(1).setText("PLAYER");
        headerRow.getCell(2).setText("CATEGORY");
        headerRow.getCell(3).setText("VALUE");
        headerRow.getCell(4).setText("RESULT");
        headerRow.getCell(5).setText("TOTAL");

        for(int i = 0; i < turns.size(); i++) {
            GameTurn turn = turns.get(i);
            XWPFTableRow row = table.createRow();

            row.getCell(0).setText(String.valueOf(i + 1)); // Turn #
            row.getCell(1).setText(truncateText(turn.getPlayer().getName(), 15));
            row.getCell(2).setText(truncateText(turn.getCategory(), 20));
            row.getCell(3).setText(String.format("$%d", turn.getQuestionValue()));

            String resultText;
            if (turn.isCorrect()) {
                resultText = "CORRECT";
            } else {
                resultText = "WRONG";
            }
            resultText += " (" + String.format("%+d", turn.getPointsEarned()) + ")";
            row.getCell(4).setText(resultText);

            row.getCell(5).setText(String.valueOf(turn.getRunningTotal()));

         }
    }
    
    /**
     * Truncates text beyond a specified maximum length,
     * appending "..." if truncated.
     *
     * @param text      the original text
     * @param maxLength maximum characters allowed
     * @return truncated text if necessary, otherwise original
     */
    private String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }



}
