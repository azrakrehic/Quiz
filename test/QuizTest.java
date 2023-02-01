import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class QuizTest {
    private static Quiz quiz;
    private static List<Question> questions;

    @BeforeAll
    static void setUp() {
        Question question1 = new Question("What are the colors on the traffic light?", 3);
        question1.addAnswer("a", "red", true);
        question1.addAnswer("b", "green", true);
        question1.addAnswer("c", "yellow", true);
        question1.addAnswer("d", "blue", false);

        Question question2 = new Question("Which months have 28 days?", 4);
        question2.addAnswer("a", "January", true);
        question2.addAnswer("b", "February", true);
        question2.addAnswer("c", "December", true);

        questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
    }

    @BeforeEach
    void setUpTest() {
        quiz = new Quiz("General knowladge quiz", GradingSystem.BINARY);
        questions.forEach(question -> quiz.addQuestion(question));
        questions.get(0).setPoints(3);
        questions.get(1).setPoints(4);
    }

    @Test
    public void testConstructor(){
        quiz = new Quiz("My quiz", GradingSystem.PARTIALLY);
        assertAll(
                () -> assertEquals("My quiz", quiz.getName()),
                () -> assertEquals(GradingSystem.PARTIALLY, quiz.getGradingSystem()),
                () -> assertTrue(quiz.getQuestions().isEmpty())
        );
    }

    @Test
    public void testConstructor2() {
        assertEquals(2, quiz.getQuestions().size());
    }

    @Test
    public void testAddQuestion1() {
        Question question3 = new Question("2+2 is? ", 1.5);
        quiz.addQuestion(question3);
        assertEquals(3, quiz.getQuestions().size());
    }

    @Test
    public void testAddQuestion2() {
        Question question3 = new Question("2+2 is? ", 1.5);
        quiz.addQuestion(question3);
        Question question4 = new Question("2+2 IS?", 1.5);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> quiz.addQuestion(question4));

        assertAll(
                () -> assertEquals("Question with this text already exists!", exception.getMessage()),
                () -> assertEquals(3, quiz.getQuestions().size())
        );
    }

    @Test
    public void testAddQuestion3() {
        Question question3 = new Question("2+2 is? ", 1.5);
        quiz.addQuestion(question3);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> quiz.addQuestion(question3));

        assertAll(
                () -> assertEquals("Question with this text already exists!", exception.getMessage()),
                () -> assertEquals(3, quiz.getQuestions().size())
        );
    }

    @Test
    public void testSetters() {
        quiz.setGradingSystem(GradingSystem.PARTIALLY);
        quiz.setName("My quiz");
        assertAll(
                () -> assertEquals(GradingSystem.PARTIALLY, quiz.getGradingSystem()),
                () -> assertEquals("My quiz", quiz.getName())
        );
    }


}
