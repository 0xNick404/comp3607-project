package com.example.report;
/**
 * Exception thrown when an error occurs during the generation of a report.
 *
 * <p>This exception is used by implementations of the {@code ReportGenerator}
 * interface—such as {@link TXTReportGenerator}—to signal failures related to
 * file writing, formatting, or data retrieval while producing game reports.</p>
 */

import java.io.IOException;

public class ReportGenerationException extends Exception {
    
/**
     * Constructs a new {@code ReportGenerationException} with the specified detail message.
     *
     * @param message A descriptive message explaining the cause of the exception.
     */
    
    public ReportGenerationException(String message) {
        super(message);
    }
    
/**
     * Constructs a new {@code ReportGenerationException} with the specified detail message
     * and underlying cause.
     *
     * @param message A descriptive message explaining the cause of the exception.
     * @param cause   The original exception that triggered this failure.
     */
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

