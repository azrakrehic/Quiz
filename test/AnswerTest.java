import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AnswerTest {
    @Test
    public void testConstructor1() {
        Answer answer = new Answer("Correct answer", true);
        assertAll(
                () -> assertEquals("Correct answer", answer.getAnswerText()),
                () -> assertTrue(answer.isCorrect())
        );
    }

    @Test
    public void testConstructor2() {
        Answer answer = new Answer("Incorrect answer", false);
        assertAll(
                () -> assertEquals("Incorrect answer", answer.getAnswerText()),
                () -> assertFalse(answer.isCorrect())
        );
    }

    @Test
    public void testSetters() {
        Answer answer = new Answer("Correct answer", true);
        answer.setAnswerText("This is not the correct answer");
        answer.setCorrect(false);
        assertAll(
                () -> assertEquals("This is not the correct answer", answer.getAnswerText()),
                () -> assertFalse(answer.isCorrect())
        );
    }
}
