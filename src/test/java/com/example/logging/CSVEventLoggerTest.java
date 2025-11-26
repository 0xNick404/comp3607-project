package com.example.logging;

// import com.example.logging.CSVEventLogger;
// import com.example.logging.EventRecord;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CSVEventLogger class.
 * Tests file creation, event writing, and null value handling.
 * 
 * @author Mahaveer Ragbir
 */
public class CSVEventLoggerTest {

    private String testFile = "test_log.csv";

    @Test
    public void loggerCreatesFileWithHeader() throws Exception {
        System.out.println("Current directory: " + new File(".").getAbsolutePath());

        // CSVEventLogger logger = new CSVEventLogger(testFile);

        // Check if file was created immediately after constructor
        File file = new File(testFile);
        System.out.println("File exists after constructor: " + file.exists());
        System.out.println("File absolute path: " + file.getAbsolutePath());

        assertTrue(file.exists(), "CSV file should be created");

        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String header = reader.readLine();
        reader.close();

        assertTrue(header.contains("Case_ID"), "Header should contain Case_ID");
        assertTrue(header.contains("Player_ID"), "Header should contain Player_ID");
    }

    @Test
    public void onEventWritesEventToFile() throws Exception {
        CSVEventLogger logger = new CSVEventLogger(testFile);

        EventRecord event = new EventRecord(
                "case-1", "player-1", "START_GAME",
                Instant.now(), null, null, null, null, null);

        logger.onEvent(event);

        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        reader.readLine(); // skip header
        String eventLine = reader.readLine();
        reader.close();

        assertTrue(eventLine.contains("case-1"), "Event line should contain case ID");
        assertTrue(eventLine.contains("START_GAME"), "Event line should contain activity");
    }

    @Test
    public void multipleEventsAreWritten() throws Exception {
        CSVEventLogger logger = new CSVEventLogger(testFile);

        EventRecord event1 = new EventRecord(
                "case-1", "player-1", "START_GAME",
                Instant.now(), null, null, null, null, null);

        EventRecord event2 = new EventRecord(
                "case-1", "player-2", "SELECT_CATEGORY",
                Instant.now(), "Science", null, null, null, null);

        logger.onEvent(event1);
        logger.onEvent(event2);

        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        reader.readLine(); // header
        String line1 = reader.readLine();
        String line2 = reader.readLine();
        reader.close();

        assertTrue(line1.contains("START_GAME"), "First line should contain START_GAME");
        assertTrue(line2.contains("SELECT_CATEGORY"), "Second line should contain SELECT_CATEGORY");
    }

    @Test
    public void onEventHandlesNullValues() throws Exception {
        CSVEventLogger logger = new CSVEventLogger(testFile);

        EventRecord event = new EventRecord(
                "case-1", null, "TEST",
                Instant.now(), null, null, null, null, null);

        logger.onEvent(event);

        File file = new File(testFile);
        assertTrue(file.exists(), "File should exist after logging event with null values");
    }
}
