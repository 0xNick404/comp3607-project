package com.example.logging;

// import com.example.logging.CSVEventLogger;
// import com.example.logging.EventRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

public class CSVEventLoggerTest {

    private String testFile = "test_log.csv";

    @AfterEach
    public void cleanup() {
        // Delete test file after each test
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
    }

    /** 
     * @throws Exception
     * 
     */
    @Test
    public void loggerCreatesFileWithHeader() throws Exception {
        // CSVEventLogger logger = new CSVEventLogger(testFile);

        File file = new File(testFile);
        assertTrue(file.exists());

        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String header = reader.readLine();
        reader.close();

        assertTrue(header.contains("Case_ID"));
        assertTrue(header.contains("Player_ID"));
    }

    /** 
     * @throws Exception
     */
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

        assertTrue(eventLine.contains("case-1"));
        assertTrue(eventLine.contains("START_GAME"));
    }

    /** 
     * @throws Exception
     */
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

        assertTrue(line1.contains("START_GAME"));
        assertTrue(line2.contains("SELECT_CATEGORY"));
    }

    /** 
     * @throws Exception
     */
    @Test
    public void onEventHandlesNullValues() throws Exception {
        CSVEventLogger logger = new CSVEventLogger(testFile);

        EventRecord event = new EventRecord(
                "case-1", null, "TEST",
                Instant.now(), null, null, null, null, null);

        logger.onEvent(event);

        // Should not crash
        File file = new File(testFile);
        assertTrue(file.exists());
    }
}
