package com.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



public class PDFReportGenerator implements  ReportGenerator {

    @Override
    public void generateReport(GameEngine session, String filePath) throws ReportGenerationException {
       // File Path handling and PDF generation logic would go here
       if(filePath == null || filePath.trim().isEmpty()) {
           String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
           filePath = "jeopardy_report_" + timestamp + ".pdf";
       } else if (!filePath.toLowerCase().endsWith(".pdf")) {
           filePath += ".pdf";
       }

       try{
        // Create PDF writer and document
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);

        try(Document document = new Document(pdfDoc)){
            document.setMargins(30,30,30,30);

            writeHeader(document);
            writeFinalScores(document, session.getPlayers());
            writeTurnByTurnHistory(document, session.getTurns());
        
        System.out.println("PDF report generated successfully: " + filePath);
         } catch (Exception e){
              throw new ReportGenerationException("Failed to write PDF report to: " + filePath, e);
         
         }
         } catch (Exception e){
              throw new ReportGenerationException("Failed to create PDF report: " + filePath, e); 
    }
}
    private void writeHeader(Document document) {
        document.add(new Paragraph("UWI COMP3607 JEOPARDY GAME - FINAL REPORT")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20));
        
        document.add(new Paragraph("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12));

         document.add(new Paragraph("\n" + "=".repeat(80) + "\n"));
    }

    private void writeFinalScores(Document document, List<Player> players){
        document.add(new Paragraph("FINAL SCORES")
                .setFontSize(16)); 

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2}));
        table.setWidth(UnitValue.createPercentValue(60));
        table.setTextAlignment(TextAlignment.LEFT);

        table.addHeaderCell(new Paragraph("RANK").setBold());
        table.addHeaderCell(new Paragraph("PLAYER NAME").setBold());
        table.addHeaderCell(new Paragraph("SCORE").setBold().setTextAlignment(TextAlignment.RIGHT));

        Optional<Player> winner = findWinner(players);
        players.sort(Comparator. comparingInt(Player::getScore).reversed());

        for(int i =0; i<players.size(); i++){
            Player p = players.get(i);

            table.addCell(String.valueOf(i+1));

            String name = p.getName();
            if(winner.isPresent() && p.equals(winner.get()) && p.getScore() >0){
                name += " *** WINNER! ***";
            }
            table.addCell(name);

            table.addCell(new Paragraph(String.format("$%d", p.getScore()))
                    .setTextAlignment(TextAlignment.RIGHT));
            
        }
        document.add(table);
        document.add(new Paragraph("\n" + "=".repeat(80) + "\n"));
    }

    private void writeTurnByTurnHistory(Document document, List<GameTurn> turns){
        document.add(new Paragraph("TURN BY TURN HISTORY").setFontSize(15));
    
        Table table = new Table(UnitValue.createPercentArray(new float[]{0.8f, 2,2,1,2,1.5f}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Paragraph("TURN").setBold());
        table.addHeaderCell(new Paragraph("PLAYER").setBold());
        table.addHeaderCell(new Paragraph("CATEGORY").setBold());
        table.addHeaderCell(new Paragraph("VALUE").setBold());
        table.addHeaderCell(new Paragraph("ANSWER").setBold());
        table.addHeaderCell(new Paragraph("RESULT").setBold());
        table.addHeaderCell(new Paragraph("TOTAL").setBold());

        for (int i = 0; i < turns.size(); i++) {
            GameTurn turn = turns.get(i);
            
            table.addCell(String.valueOf(i + 1)); // Turn #
            table.addCell(truncateText(turn.getPlayer().getName(), 15));
            table.addCell(truncateText(turn.getCategory(), 20));
            
            
            table.addCell(new Paragraph(String.format("$%d", turn.getQuestionValue()))
                    .setTextAlignment(TextAlignment.RIGHT));
            
            
            String resultText;
            if (turn.isCorrect()) {
                resultText = "CORRECT";
            } else {
                resultText = "WRONG";
            }
            resultText += " (" + String.format("%+d", turn.getPointsEarned()) + ")";
            table.addCell(resultText);
            
            
            table.addCell(new Paragraph(String.valueOf(turn.getRunningTotal()))
                    .setTextAlignment(TextAlignment.RIGHT));
    }
    document.add(table);
}

private Optional<Player> findWinner(List<Player> players) {
    return players.stream()
            .max(Comparator.comparingInt(Player::getScore));

}

private String truncateText(String text, int maxLength) {
    if (text.length() <= maxLength) {
        return text;
    }
    return text.substring(0, maxLength - 3) + "...";
}

}