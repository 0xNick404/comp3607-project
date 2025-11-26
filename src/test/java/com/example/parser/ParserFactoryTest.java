package com.example.parser;

import com.example.parser.factory.ParserFactory;

import com.example.parser.strategy.FileParserStrategy;
import com.example.parser.strategy.CSVFileParser;
import com.example.parser.strategy.JSONFileParser;
import com.example.parser.strategy.XMLFileParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserFactoryTest {

    @Test
    public void csvParserReturned() {
        FileParserStrategy p = ParserFactory.createParser("data/questions.csv");
        assertTrue(p instanceof CSVFileParser);
    }

    @Test
    public void jsonParserReturned() {
        FileParserStrategy p = ParserFactory.createParser("data/questions.json");
        assertTrue(p instanceof JSONFileParser);
    }

    @Test
    public void xmlParserReturned() {
        FileParserStrategy p = ParserFactory.createParser("data/questions.xml");
        assertTrue(p instanceof XMLFileParser);
    }
}
