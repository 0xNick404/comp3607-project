package com.example.parser.strategy;

import com.example.model.Question;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Concrete class of {@link FileParserStrategy}.
 * Parses a JSON file and returns a list of Question objects.
 * Uses json-simple library.
 * @author Nicholas Grimes
 */
public class JSONFileParser implements FileParserStrategy{
    /**
     * Parses question data from a JSON-formatted input stream.
     * @param inputStream the JSON data stream
     * @return a list of parsed {@link Question} objects extracted from the JSON file
     */
    @Override
    public List<Question> parse(InputStream inputStream){
        List<Question> questions = new ArrayList<>();
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new InputStreamReader(inputStream));

            JSONParser jsonParser = new JSONParser();
            JSONArray questionsArray = (JSONArray) jsonParser.parse(reader);

            for(Object obj : questionsArray){
                JSONObject questionJSON = (JSONObject) obj;

                String category = (String) questionJSON.get("Category");

                Object valueObj = questionJSON.get("Value");
                String value = valueObj.toString();

                String question = (String) questionJSON.get("Question");

                JSONObject optionsObj = (JSONObject) questionJSON.get("Options");

                String[] options = {
                    (String) optionsObj.get("A"),
                    (String) optionsObj.get("B"),
                    (String) optionsObj.get("C"),
                    (String) optionsObj.get("D")
                };

                String correctAnswer = (String) questionJSON.get("CorrectAnswer");

                questions.add(new Question(category, value, question, options, correctAnswer));
            }
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return questions;
    }
}
