package com.example;

import com.example.gameplay.state.GameEngine;
import com.example.model.Player;
import com.example.model.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PDFReportGeneratorTest {

    private PDFReportGenerator generator;
    private GameEngine gameEngine;
    private List<Question> questions;
    private String testFilePath = "test_report.pdf";

    @BeforeEach
    public void setup() {
        generator = new PDFReportGenerator();

        // Create test questions
        questions = new ArrayList<>();
        String[] options = { "A", "B", "C", "D" };
        questions.add(new Question("Science", "100", "Test question?", options, "A"));

        // Create game engine
        gameEngine = new GameEngine(questions, null, "test-case");
    }

    @AfterEach
    public void cleanup() {
        // Delete test file
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }

        // Delete any auto-generated files
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.startsWith("jeopardy_report_") && name.endsWith(".pdf"));
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    @Test
    public void generateReportCreatesFile() throws ReportGenerationException {
        generator.generateReport(gameEngine, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "PDF file should be created");
        assertTrue(file.length() > 0, "PDF file should not be empty");
    }

    @Test
    public void generateReportWithNullPathCreatesTimestampedFile() throws ReportGenerationException {
        generator.generateReport(gameEngine, null);

        // Check if a timestamped file was created
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.startsWith("jeopardy_report_") && name.endsWith(".pdf"));

        assertNotNull(files);
        assertTrue(files.length > 0, "Timestamped PDF should be created");

        // Cleanup
        for (File f : files) {
            f.delete();
        }
    }

    @Test
    public void generateReportAddsExtensionIfMissing() throws ReportGenerationException {
        String pathWithoutExtension = "test_report_no_ext";
        generator.generateReport(gameEngine, pathWithoutExtension);

        File file = new File(pathWithoutExtension + ".pdf");
        assertTrue(file.exists(), "PDF extension should be added automatically");

        // Cleanup
        file.delete();
    }

    @Test
    public void generateReportThrowsExceptionForInvalidPath() {
        String invalidPath = "/invalid/path/that/does/not/exist/report.pdf";

        assertThrows(ReportGenerationException.class, () -> {
            generator.generateReport(gameEngine, invalidPath);
        });
    }
}