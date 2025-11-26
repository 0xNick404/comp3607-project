package com.example.logging;

import java.io.FileWriter;
import java.io.IOException;

public class CSVEventLogger implements EventListener {
    private String filename;

    public CSVEventLogger(String filename) {
        this.filename = filename;
        writeHeader();
    }

    // Write CSV header
    private void writeHeader() {
        // try catch block to handle file writing exceptions
        try {
            FileWriter writer = new FileWriter(filename, false); // false = overwrite
            writer.write(
                    "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing header: " + e.getMessage());
        }
    }

    /**
     * @param event
     *              onEvent method to log event details into CSV file
     */
    @Override
    public void onEvent(EventRecord event) {
        try {
            FileWriter writer = new FileWriter(filename, true); // true = append

            String caseId = event.getCaseId() != null ? event.getCaseId() : "";
            String playerId = event.getPlayerId() != null ? event.getPlayerId() : "";
            String activity = event.getActivity() != null ? event.getActivity() : "";

            String timestamp = "";
            if (event.getTimestamp() != null) {
                timestamp = event.getTimestamp().toString();
            }

            String category = event.getCategory() != null ? event.getCategory() : "";
            String questionValue = event.getQuestionValue() != null ? event.getQuestionValue().toString() : "";
            String answerGiven = event.getAnswerGiven() != null ? event.getAnswerGiven() : "";
            String result = event.getResult() != null ? event.getResult() : "";
            String score = event.getScoreAfterPlay() != null ? event.getScoreAfterPlay().toString() : "";

            String line = caseId + "," + playerId + "," + activity + "," + timestamp + "," +
                    category + "," + questionValue + "," + answerGiven + "," + result + "," + score + "\n";

            writer.write(line);
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing event: " + e.getMessage());
        }
    }
}