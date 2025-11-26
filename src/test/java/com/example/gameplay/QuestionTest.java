package com.example.gameplay;

import com.example.model.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    private Question question;
    private String[] options;

    @BeforeEach
    public void setup() {
        options = new String[] { "Option A", "Option B", "Option C", "Option D" };
        question = new Question("Science", "100", "What is H2O?", options, "Water");
    }

    @Test
    public void getCategoryReturnsCorrectCategory() {
        assertEquals("Science", question.getCategory());
    }

    @Test
    public void getValueReturnsCorrectValue() {
        assertEquals(100, question.getValue());
    }

    @Test
    public void getQuestionTextReturnsCorrectText() {
        assertEquals("What is H2O?", question.getQuestionText());
    }

    @Test
    public void getOptionsReturnsAllOptions() {
        String[] result = question.getOptions();
        assertEquals(4, result.length);
        assertEquals("Option A", result[0]);
    }

    @Test
    public void getCorrectAnswerReturnsCorrectAnswer() {
        assertEquals("Water", question.getCorrectAnswer());
    }

    @Test
    public void questionStartsUnpicked() {
        assertFalse(question.hasBeenPicked());
    }

    @Test
    public void markPickedSetsQuestionAsPicked() {
        question.markPicked();
        assertTrue(question.hasBeenPicked());
    }

    @Test
    public void valueHandlesInvalidNumber() {
        Question invalidQuestion = new Question("Math", "invalid", "Test?", options, "Answer");
        assertEquals(0, invalidQuestion.getValue());
    }
}
