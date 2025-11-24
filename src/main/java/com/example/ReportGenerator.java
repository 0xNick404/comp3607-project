/**
 * Defines the contract for generating game reports in different formats.
 *
 * <p>Implementations of this interface (such as {@link TXTReportGenerator})
 * are responsible for exporting details of a {@link GameEngine} session to
 * a specified file, including scores, turn history, and other metadata.</p>
 */

package com.example;

public interface ReportGenerator{

  /**
     * Generates a report for the given game session and writes it to the specified file path.
     *
     * <p>Implementations may enforce file-type extensions or generate default filenames
     * when {@code filePath} is null or empty.</p>
     *
     * @param session  The game session containing all data needed for the report.
     * @param filePath The output path for the generated report. May be null or blank.
     * @throws ReportGenerationException If any error occurs during report creation or writing.
     */
  public void generateReport (GameEngine session, String filePath) throws ReportGenerationException;

}
