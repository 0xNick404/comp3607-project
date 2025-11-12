package com.example.strategy;

import com.example.model.Question;

import java.util.List;
import java.io.InputStream;

/**
 * Defines the strategy interface for parsing question data from various file formats.
 * Follows the Strategy Design Pattern.
 */

public interface FileParserStrategy{
    List<Question> parse(InputStream inputStream);
}
