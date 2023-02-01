import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizResultTest {
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
    }

    @Test
    public void testConstructor() {
        QuizResult quizResult = new QuizResult(quiz);
        assertAll(
                () -> assertEquals(quiz, quizResult.getQuiz()),
                () -> assertEquals(0, quizResult.getTotalPoints()),
                () -> assertEquals(0,quizResult.getQuestionPoints().size())
        );
    }

    @Test
    public void testQuizResult() {
        Map<Question, ArrayList<String>> answeredQuestions = new HashMap<>();
        answeredQuestions.put(questions.get(0), new ArrayList<>(List.of("a","c","b")));
        answeredQuestions.put(questions.get(1), new ArrayList<>(List.of("c")));
        QuizResult quizResult = quiz.submitQuiz(answeredQuestions);
        assertAll(
                () -> assertEquals(3, quizResult.getTotalPoints()),
                () -> assertEquals(2,quizResult.getQuestionPoints().size()),
                () -> assertTrue(quizResult.getQuestionPoints().containsKey(questions.get(0))),
                () -> assertEquals(3, quizResult.getQuestionPoints().get(questions.get(0))),
                () -> assertEquals(0, quizResult.getQuestionPoints().get(questions.get(1)))
        );
    }
}
