package com.example.parser.strategy;

import com.example.model.Question;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

/**
 * Concrete class of {@link FileParserStrategy}.
 * Parses a CSV file and returns a list of Question objects.
 * Uses OpenCSV library.
 * Expected format:
 * Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer
 * @author Nicholas Grimes
 */
public class CSVFileParser implements FileParserStrategy{
    /**
     * Parses question data from a CSV-formatted input stream.
     * Each line is expected to represent one question.
     * @param inputStream the CSV data stream
     * @return a list of parsed {@link Question} objects extracted from the CSV file
     */
    @Override
    public List<Question> parse(InputStream inputStream){
        List<Question> questions = new ArrayList<>();
        CSVReader reader = null;
        String line[];

        try{
            reader = new CSVReader(new InputStreamReader(inputStream));

            line = reader.readNext();

            String header[] = {
                "Category",
                "Value",
                "Question",
                "OptionA",
                "OptionB",
                "OptionC",
                "OptionD",
                "CorrectAnswer"
            };

            if(line == null) throw new IllegalArgumentException("File is empty");
            if(!Arrays.equals(line,header))
                throw new IllegalArgumentException("File has wrong columns: " + line);

            while((line = reader.readNext()) != null){
                String category = line[0];
                String value = line[1];
                String questionText = line[2];

                String options[] = {
                    line[3],
                    line[4],
                    line[5],
                    line[6]
                };

                String correctAnswer = line[7];

                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        }
        catch(IOException | CsvValidationException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        return questions;
    }
}
