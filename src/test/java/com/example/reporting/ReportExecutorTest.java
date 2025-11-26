package com.example.reporting;

import com.example.ReportExecutor;
import com.example.gameplay.state.GameEngine;
import com.example.model.Player;
import com.example.model.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReportExecutor class.
 * Tests multi-format report generation and error handling.
 * 
 * @author Mahaveer Ragbir
 */
public class ReportExecutorTest {

    private GameEngine gameEngine;
    private List<Question> questions;

    @BeforeEach
    public void setup() {
        /** Create some test questions */
        questions = new ArrayList<>();
        String[] options = { "A", "B", "C", "D" };
        questions.add(new Question("Science", "100", "What is H2O?", options, "Water"));

        /** Create test players without requiring input */
        List<Player> testPlayers = Arrays.asList(
                new Player(1, "Test Player 1"),
                new Player(2, "Test Player 2"));

        /** Use the test constructor that accepts pre-defined players */
        gameEngine = new GameEngine(questions, testPlayers, null, "test-case");
    }

    @AfterEach
    public void cleanup() {
        /** Delete generated report files */
        deleteFileIfExists("jeopardy_report.txt");
        deleteFileIfExists("jeopardy_report.pdf");
        deleteFileIfExists("jeopardy_report.docx");
    }

    @Test
    public void executeReportsCreatesFiles() {
        ReportExecutor.executeReports(gameEngine);

        /** Check if files were created */
        File txtFile = new File("jeopardy_report.txt");
        File pdfFile = new File("jeopardy_report.pdf");
        File docxFile = new File("jeopardy_report.docx");

        assertTrue(txtFile.exists(), "TXT report should be created");
        assertTrue(pdfFile.exists(), "PDF report should be created");
        assertTrue(docxFile.exists(), "DOCX report should be created");
    }

    @Test
    public void executeReportsDoesNotCrashWithNullPublisher() {
        assertDoesNotThrow(() -> ReportExecutor.executeReports(gameEngine));
    }

    private void deleteFileIfExists(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
