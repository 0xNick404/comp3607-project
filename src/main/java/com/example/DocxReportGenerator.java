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



public class DocxReportGenerator implements ReportGenerator {

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

    private Optional<Player> findWinner(List<Player> players){
        return players.stream()
                .max(Comparator.comparingInt(Player::getScore));
    }

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
    private String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }


}