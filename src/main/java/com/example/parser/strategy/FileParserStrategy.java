package com.example.parser.strategy;

import com.example.model.Question;

import java.util.List;
import java.io.InputStream;

/**
 * Defines the strategy interface for parsing question data from various file formats.
 * Follows the Strategy Design Pattern allowing the parser to vary independently based on the file type.
 * @author Nicholas Grimes
 */
public interface FileParserStrategy{
    /**
     * Parses question data from the given input stream.
     * @param inputStream the input stream containing file data
     * @return a list of parsed {@link Question} objects
     */
    List<Question> parse(InputStream inputStream);
}
