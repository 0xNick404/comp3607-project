package com.example.parser.strategy;

import com.example.model.Question;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Concrete class of {@link FileParserStrategy}.
 * Parses an XML file and returns a list of Question objects.
 * Uses the DOM (Document Object Model) approach for XML parsing.
 * @author Nicholas Grimes
 */
public class XMLFileParser implements FileParserStrategy{
    /**
     * Parses question data from an XML-formatted input stream.
     * @param inputStream the XML data stream
     * @return a list of parsed {@link Question} objects extracted from the XML file
     */
    @Override
    public List<Question> parse(InputStream inputStream){
        List<Question> questions = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            NodeList questionItemList = doc.getElementsByTagName("QuestionItem");

            for(int i = 0; i < questionItemList.getLength(); i++){
                Node n = questionItemList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    Element questionItem = (Element) n;

                    String category = questionItem.getElementsByTagName("Category").item(0).getTextContent();
                    String value = questionItem.getElementsByTagName("Value").item(0).getTextContent();
                    String questionText = questionItem.getElementsByTagName("QuestionText").item(0).getTextContent();

                    Element optionsElement = (Element) questionItem.getElementsByTagName("Options").item(0);
                    String[] options = {
                        optionsElement.getElementsByTagName("OptionA").item(0).getTextContent(),
                        optionsElement.getElementsByTagName("OptionB").item(0).getTextContent(),
                        optionsElement.getElementsByTagName("OptionC").item(0).getTextContent(),
                        optionsElement.getElementsByTagName("OptionD").item(0).getTextContent()
                    };

                    String correctAnswer = questionItem.getElementsByTagName("CorrectAnswer").item(0).getTextContent();

                    questions.add(new Question(category, value, questionText, options, correctAnswer));
                }
            }
        }
        catch(ParserConfigurationException e){
            e.printStackTrace();
        }
        catch(SAXException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return questions;
    }
}
