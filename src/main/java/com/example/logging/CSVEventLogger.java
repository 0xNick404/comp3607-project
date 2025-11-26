package com.example.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

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
        System.out.println("=== CSVEventLogger Constructor ===");
        System.out.println("Filename: " + filename);
        System.out.println("Absolute path: " + new File(filename).getAbsolutePath());
        System.out.println("Current directory: " + new File(".").getAbsolutePath());
        writeHeader();
    }

    /**
     * Writes the CSV header row to the file.
     * This method overwrites any existing file content.
     */
    private void writeHeader() {
        try {
            System.out.println("=== writeHeader() ===");
            File file = new File(filename);
            System.out.println("File object created: " + file.getAbsolutePath());

            // FIX: Check if parent directory exists, but handle null case
            File parentDir = file.getParentFile();
            if (parentDir != null) {
                System.out.println("Parent directory exists: " + parentDir.exists());
            } else {
                System.out.println("No parent directory (file in current directory)");
            }

            FileWriter writer = new FileWriter(filename, false);
            System.out.println("FileWriter created successfully");

            writer.write(
                    "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            System.out.println("Header written to buffer");

            writer.flush();
            System.out.println("Data flushed to disk");

            writer.close();
            System.out.println("FileWriter closed");

            // Verify file was actually created
            System.out.println("File exists after creation: " + file.exists());
            System.out.println("File size: " + file.length());
            System.out.println("=== writeHeader() COMPLETE ===");

        } catch (IOException e) {
            System.err.println("ERROR in writeHeader: " + e.getMessage());
            e.printStackTrace();
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
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.err.println("Error writing event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
