package com.example;

public interface ReportGenerator{
  public void generateReport (GameEngine session, String filePath) throws ReportGenerationException;
}