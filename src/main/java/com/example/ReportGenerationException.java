package com.example;
/**
 * Exception thrown when an error occurs during the generation of a report.
 *
 * <p>This exception is used by implementations of the {@code ReportGenerator}
 * interface—such as {@link TXTReportGenerator}—to signal failures related to
 * file writing, formatting, or data retrieval while producing game reports.</p>
 */
public class ReportGenerationException extends Exception {

    public ReportGenerationException(String message) {
        super(message);
    }

    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

