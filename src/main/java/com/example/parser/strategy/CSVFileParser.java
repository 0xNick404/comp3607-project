package com.example.parser.strategy;

import java.util.List;

import com.example.model.Question;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

/**
 * Parses a CSV file and returns a list of Question objects.
 * Expected format:
 * Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer
 * Each line represents one question record.
 */

public class CSVFileParser implements FileParserStrategy{
    @Override
    public List<Question> parse(InputStream inputStream){
        List<Question> questions = new ArrayList<>();
        CSVReader reader = null;
        String line[];

        try{
            reader = new CSVReader(new InputStreamReader(inputStream));

            /** Read first line */
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

            /** Make sure file is not empty and has correct headers */
            if(line == null) throw new IllegalArgumentException("File is empty");
            if(!Arrays.equals(line,header))
                throw new IllegalArgumentException("File has wrong columns: " + line);

            /** Run through following lines */
            while((line = reader.readNext()) != null){
                /** Assign values */
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

                /** Store in Question */
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
