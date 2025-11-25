/**
 * ReportExecutor.java
 * Handles the execution of multiple report generation strategies.
 * @author Shiann
 */
package com.example;

import java.util.List;

import com.example.gameplay.state.GameEngine;

public class ReportExecutor {

    /**
     * Executes all ReportGenerator strategies to produce output files.
     * @param session The GameEngine object containing all final scores and turn history.
     */
    public static void executeReports(GameEngine session) {
        List<ReportGenerator> generators = List.of(
            new TXTReportGenerator(),
            new PDFReportGenerator(),
            new DocxReportGenerator()
        );

        List<String> filePaths = List.of(
            "jeopardy_report.txt",
            "jeopardy_report.pdf",
            "jeopardy_report.docx"
        );

        for (int i = 0; i < generators.size(); i++) {
            ReportGenerator generator = generators.get(i);
            String filePath = filePaths.get(i);
            try {
                generator.generateReport(session, filePath);
            } catch (ReportGenerationException e) {
                System.err.println("Error generating report: " + e.getMessage());
            }
        }
    }
}
