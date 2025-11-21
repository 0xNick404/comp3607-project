package com.example.parser.strategy;

import java.util.List;

import com.example.model.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
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
        BufferedReader reader = null;
        String line = "";

        try{
            reader = new BufferedReader(new InputStreamReader(inputStream));

            /** Read first line */
            line = reader.readLine();

            /** Make sure file is not empty and has correct headers */
            if(line == null) throw new IllegalArgumentException("File is empty");
            if(!line.equals("Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer"))
                throw new IllegalArgumentException("File has wrong columns: " + line);

            /** Run through following lines */
            while((line = reader.readLine()) != null){
                /** Split values */
                String[] question = line.split(",");

                /** Assign values */
                String category = question[0];
                String value = question[1];
                String questionText = question[2];

                String options[] = {
                    question[3],
                    question[4],
                    question[5],
                    question[6]
                };

                String correctAnswer = question[7];

                /** Store in Question */
                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        }
        catch(Exception e){
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
