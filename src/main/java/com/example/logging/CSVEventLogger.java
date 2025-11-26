package com.example.logging;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Event listener implementation that logs events to a CSV file.
 * Creates a CSV file with headers and appends event records as they occur.
 * Each event is written as a new line in the CSV file with all event fields.
 * 
 * @author Mahaveer Ragbir
 */
public class CSVEventLogger implements EventListener {
    private String filename;

    /**
     * Constructs a new CSVEventLogger and initializes the CSV file with headers.
     * If the file already exists, it will be overwritten.
     * 
     * @param filename the path and name of the CSV file to create
     */
    public CSVEventLogger(String filename) {
        this.filename = filename;
        writeHeader();
    }

    /**
     * Writes the CSV header row to the file.
     * This method overwrites any existing file content.
     */
    private void writeHeader() {
        try {
            FileWriter writer = new FileWriter(filename, false); 
            writer.write(
                    "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing header: " + e.getMessage());
        }
    }

    /**
     * Logs an event record to the CSV file.
     * Appends a new line to the CSV file containing all event fields.
     * Null values are converted to empty strings.
     * 
     * @param event the event record to log
     */
    @Override
    public void onEvent(EventRecord event) {
        try {
            FileWriter writer = new FileWriter(filename, true); 

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
